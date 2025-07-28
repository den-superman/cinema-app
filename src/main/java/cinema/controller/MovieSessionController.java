package cinema.controller;

import cinema.dto.request.MovieSessionRequestDto;
import cinema.dto.response.MovieSessionResponseDto;
import cinema.model.MovieSession;
import cinema.service.MovieSessionService;
import cinema.service.mapper.RequestDtoMapper;
import cinema.service.mapper.ResponseDtoMapper;
import cinema.util.DateTimePatternUtil;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/movie-sessions")
public class MovieSessionController {
    private final MovieSessionService movieSessionService;
    private final RequestDtoMapper<MovieSessionRequestDto, MovieSession>
            movieSessionRequestDtoMapper;
    private final ResponseDtoMapper<MovieSessionResponseDto, MovieSession>
            movieSessionResponseDtoMapper;

    public MovieSessionController(MovieSessionService movieSessionService,
            RequestDtoMapper<MovieSessionRequestDto, MovieSession> movieSessionRequestDtoMapper,
            ResponseDtoMapper<MovieSessionResponseDto, MovieSession>
                                      movieSessionResponseDtoMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionRequestDtoMapper = movieSessionRequestDtoMapper;
        this.movieSessionResponseDtoMapper = movieSessionResponseDtoMapper;
    }

    @PostMapping
    @ResponseBody
    public MovieSessionResponseDto add(@RequestBody @Valid MovieSessionRequestDto requestDto) {
        MovieSession movieSession = movieSessionRequestDtoMapper.mapToModel(requestDto);
        movieSessionService.add(movieSession);
        return movieSessionResponseDtoMapper.mapToDto(movieSession);
    }

    @GetMapping("/available")
    public String findAvailableSessions(@RequestParam Long movieId, Model model
//                                                @RequestParam
//            @DateTimeFormat(pattern = DateTimePatternUtil.DATE_PATTERN) LocalDate date
            ) {
        LocalDate date = LocalDate.now();
        List<MovieSession> availableSessions = movieSessionService.findAvailableSessions(movieId, date);
        model.addAttribute("movieSessions", availableSessions);
        return "movie-sessions";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public MovieSessionResponseDto update(@PathVariable Long id,
                                          @RequestBody @Valid MovieSessionRequestDto requestDto) {
        MovieSession movieSession = movieSessionRequestDtoMapper.mapToModel(requestDto);
        movieSession.setId(id);
        movieSessionService.update(movieSession);
        return movieSessionResponseDtoMapper.mapToDto(movieSession);
    }

    @GetMapping("/{id}/seats")
    public String getSeats(@PathVariable Long id, Model model) {
        model.addAttribute("seatMatrix", movieSessionService.get(id).getOccupiedSeats());
        System.out.println("Seat Matrix: " + Arrays.deepToString(movieSessionService.get(id).getOccupiedSeats()));
        return "seats";
    }

    @PostMapping("/{id}/confirm-seats")
    public ResponseEntity<String> confirmSeats(@PathVariable Long id, @RequestBody Map<String, List<String>> payload) {
        List<String> selectedSeats = payload.get("seats");

        if (selectedSeats == null || selectedSeats.isEmpty()) {
            return ResponseEntity.badRequest().body("No seats selected");
        }

        MovieSession movieSession = movieSessionService.get(id);
        boolean[][] occupiedSeats = movieSession.getOccupiedSeats();
        selectedSeats.forEach(s -> {
            int row = Integer.parseInt(s.split("-")[0]);
            int seat = Integer.parseInt(s.split("-")[1]);
            occupiedSeats[row-1][seat-1] = true;
        });
        movieSession.setOccupiedSeats(occupiedSeats);
        movieSessionService.update(movieSession);

        System.out.println("Selected seats: " + selectedSeats);

        // Return a success response
        return ResponseEntity.ok("Seats successfully confirmed");
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        movieSessionService.delete(id);
    }
}
