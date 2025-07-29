package cinema.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MovieSessionRequestDto {
  @Min(1)
  private Long movieId;

  @Min(1)
  private Long cinemaHallId;

  @NotNull private LocalDateTime showTime;

  public Long getMovieId() {
    return movieId;
  }

  public Long getCinemaHallId() {
    return cinemaHallId;
  }

  public LocalDateTime getShowTime() {
    return showTime;
  }
}
