package cinema.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MovieSessionRequestDto {
  @Min(1)
  private Long movieId;

  @Min(1)
  private Long cinemaHallId;

  @NotNull private LocalDateTime showTime;
}
