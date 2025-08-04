package cinema.service.mapper;

import cinema.dto.request.MovieSessionRequestDto;
import cinema.dto.response.MovieSessionResponseDto;
import cinema.model.CinemaHall;
import cinema.model.MovieSession;
import cinema.service.CinemaHallService;
import cinema.service.MovieService;
import org.springframework.stereotype.Component;

@Component
public class MovieSessionMapper
    implements RequestDtoMapper<MovieSessionRequestDto, MovieSession>,
        ResponseDtoMapper<MovieSessionResponseDto, MovieSession> {
  private final CinemaHallService cinemaHallService;
  private final MovieService movieService;

  public MovieSessionMapper(CinemaHallService cinemaHallService, MovieService movieService) {
    this.cinemaHallService = cinemaHallService;
    this.movieService = movieService;
  }

  @Override
  public MovieSession mapToModel(MovieSessionRequestDto dto) {
    CinemaHall cinemaHall = cinemaHallService.get(dto.getCinemaHallId());
    MovieSession movieSession = new MovieSession(cinemaHall.getRows(), cinemaHall.getSeatsInRow());
    movieSession.setMovie(movieService.get(dto.getMovieId()));
    movieSession.setCinemaHall(cinemaHall);
    movieSession.setShowTime(dto.getShowTime());
    return movieSession;
  }

  @Override
  public MovieSessionResponseDto mapToDto(MovieSession movieSession) {
    MovieSessionResponseDto responseDto = new MovieSessionResponseDto();
    responseDto.setMovieSessionId(movieSession.getId());
    responseDto.setCinemaHallId(movieSession.getCinemaHall().getId());
    responseDto.setMovieId(movieSession.getMovie().getId());
    responseDto.setMovieTitle(movieSession.getMovie().getTitle());
    responseDto.setShowTime(movieSession.getShowTime());
    return responseDto;
  }
}
