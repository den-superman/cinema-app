package cinema.controller;

import cinema.dao.TicketDao;
import cinema.dto.request.MovieSessionRequestDto;
import cinema.dto.response.MovieSessionResponseDto;
import cinema.model.MovieSession;
import cinema.model.Ticket;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import cinema.service.MovieSessionService;
import cinema.service.UserService;
import cinema.service.mapper.RequestDtoMapper;
import cinema.service.mapper.ResponseDtoMapper;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movie-sessions")
public class MovieSessionController {
  private final MovieSessionService movieSessionService;
  private final UserService userService;
  private final TicketDao ticketDao;
  private final RequestDtoMapper<MovieSessionRequestDto, MovieSession> movieSessionRequestDtoMapper;
  private final ResponseDtoMapper<MovieSessionResponseDto, MovieSession>
      movieSessionResponseDtoMapper;
  private final MovieService movieService;
  private final CinemaHallService cinemaHallService;

  public MovieSessionController(
      MovieSessionService movieSessionService,
      UserService userService,
      TicketDao ticketDao,
      RequestDtoMapper<MovieSessionRequestDto, MovieSession> movieSessionRequestDtoMapper,
      ResponseDtoMapper<MovieSessionResponseDto, MovieSession> movieSessionResponseDtoMapper,
      MovieService movieService,
      CinemaHallService cinemaHallService) {
    this.movieSessionService = movieSessionService;
    this.userService = userService;
    this.ticketDao = ticketDao;
    this.movieSessionRequestDtoMapper = movieSessionRequestDtoMapper;
    this.movieSessionResponseDtoMapper = movieSessionResponseDtoMapper;
    this.movieService = movieService;
    this.cinemaHallService = cinemaHallService;
  }

  @PostMapping
  public String add(
      @ModelAttribute @Valid MovieSessionRequestDto requestDto, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("halls", cinemaHallService.getAll());
      model.addAttribute("movies", movieService.getAll());
      return "movie-session-form"; // Имя Thymeleaf-шаблона, где форма
    }

    MovieSession movieSession = movieSessionRequestDtoMapper.mapToModel(requestDto);
    movieSessionService.add(movieSession);
    return String.format("redirect:/movie-sessions/available?movieId=%s", requestDto.getMovieId());
  }

  @GetMapping("/new")
  public String showCreateForm(Model model) {
    model.addAttribute("movieSession", new MovieSession());
    model.addAttribute("halls", cinemaHallService.getAll());
    model.addAttribute("movies", movieService.getAll());
    return "admin/movie-session-form";
  }

  @GetMapping("/available")
  public String findAvailableSessions(@RequestParam Long movieId, Model model) {
    LocalDateTime dateTime = LocalDateTime.now();
    List<MovieSession> availableSessions =
        movieSessionService.findAvailableSessions(movieId, dateTime);
    model.addAttribute("movieSessions", availableSessions);
    return "movie-sessions";
  }

  @PutMapping("/{id}")
  @ResponseBody
  public MovieSessionResponseDto update(
      @PathVariable Long id, @RequestBody @Valid MovieSessionRequestDto requestDto) {
    MovieSession movieSession = movieSessionRequestDtoMapper.mapToModel(requestDto);
    movieSession.setId(id);
    movieSessionService.update(movieSession);
    return movieSessionResponseDtoMapper.mapToDto(movieSession);
  }

  @GetMapping("/{id}/seats")
  public String getSeats(@PathVariable Long id, Model model) {
    model.addAttribute("seatMatrix", movieSessionService.get(id).getOccupiedSeats());
    return "seats";
  }

  @PostMapping("/{id}/confirm-seats")
  public ResponseEntity<String> confirmSeats(
      @PathVariable Long id, @RequestBody Map<String, List<String>> payload) {
    List<String> selectedSeats = payload.get("seats");

    if (selectedSeats == null || selectedSeats.isEmpty()) {
      return ResponseEntity.badRequest().body("No seats selected");
    }
    MovieSession movieSession = movieSessionService.get(id);
    boolean[][] occupiedSeats = movieSession.getOccupiedSeats();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    selectedSeats.forEach(
        s -> {
          Ticket ticket = new Ticket();
          ticket.setMovieSession(movieSession);
          ticket.setUser(userService.findByEmail(username).get());
          int line = Integer.parseInt(s.split("-")[0]);
          ticket.setLine(line);
          int seat = Integer.parseInt(s.split("-")[1]);
          ticket.setSeat(seat);
          ticketDao.save(ticket);
          occupiedSeats[line - 1][seat - 1] = true;
        });

    movieSession.setOccupiedSeats(occupiedSeats);
    movieSessionService.update(movieSession);

    return ResponseEntity.ok("Seats successfully confirmed");
  }

  @DeleteMapping("/{id}")
  @ResponseBody
  public void delete(@PathVariable Long id) {
    movieSessionService.delete(id);
  }
}
