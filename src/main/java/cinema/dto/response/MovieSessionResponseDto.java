package cinema.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MovieSessionResponseDto {
  private Long movieSessionId;
  private Long movieId;
  private String movieTitle;
  private Long cinemaHallId;
  private LocalDateTime showTime;

}
