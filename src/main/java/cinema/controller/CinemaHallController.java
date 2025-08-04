package cinema.controller;

import cinema.dto.request.CinemaHallRequestDto;
import cinema.dto.response.CinemaHallResponseDto;
import cinema.model.CinemaHall;
import cinema.service.CinemaHallService;
import cinema.service.mapper.RequestDtoMapper;
import cinema.service.mapper.ResponseDtoMapper;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cinema-halls")
public class CinemaHallController {
  private final CinemaHallService cinemaHallService;
  private final RequestDtoMapper<CinemaHallRequestDto, CinemaHall> cinemaHallRequestDtoMapper;
  private final ResponseDtoMapper<CinemaHallResponseDto, CinemaHall> cinemaHallResponseDtoMapper;

  public CinemaHallController(
      CinemaHallService cinemaHallService,
      RequestDtoMapper<CinemaHallRequestDto, CinemaHall> cinemaHallRequestDtoMapper,
      ResponseDtoMapper<CinemaHallResponseDto, CinemaHall> cinemaHallResponseDtoMapper) {
    this.cinemaHallService = cinemaHallService;
    this.cinemaHallRequestDtoMapper = cinemaHallRequestDtoMapper;
    this.cinemaHallResponseDtoMapper = cinemaHallResponseDtoMapper;
  }

  @GetMapping("/new")
  public String showCreateForm() {
    return "admin/cinema-hall-form";
  }

  @PostMapping
  public String add(
      @ModelAttribute @Valid CinemaHallRequestDto requestDto, BindingResult result, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("message", "Failed to add movie");
      return "movie-form";
    }

    cinemaHallService.add(cinemaHallRequestDtoMapper.mapToModel(requestDto));
    return "redirect:/movies";
  }

  @GetMapping
  public List<CinemaHallResponseDto> getAll() {
    return cinemaHallService.getAll().stream().map(cinemaHallResponseDtoMapper::mapToDto).toList();
  }
}
