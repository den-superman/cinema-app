package cinema.controller;

import cinema.dto.request.MovieRequestDto;
import cinema.model.Movie;
import cinema.service.MovieService;
import cinema.service.mapper.RequestDtoMapper;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movies")
public class MovieController {
  private final MovieService movieService;
  private final RequestDtoMapper<MovieRequestDto, Movie> movieRequestDtoMapper;

  public MovieController(
      MovieService movieService, RequestDtoMapper<MovieRequestDto, Movie> movieRequestDtoMapper) {
    this.movieService = movieService;
    this.movieRequestDtoMapper = movieRequestDtoMapper;
  }

  @PostMapping
  public String add(
      @ModelAttribute @Valid MovieRequestDto requestDto, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("message", "Failed to add movie");
      return "movie-form";
    }

    movieService.add(movieRequestDtoMapper.mapToModel(requestDto));
    return "redirect:/movies";
  }

  @GetMapping("/new")
  public String showCreateForm() {
    return "admin/movie-form";
  }

  @GetMapping
  public String showMovies(Model model) {
    List<Movie> movies = movieService.getAll().stream().toList();
    model.addAttribute("movies", movies);
    return "movies";
  }
}
