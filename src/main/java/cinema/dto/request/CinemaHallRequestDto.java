package cinema.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CinemaHallRequestDto {
  @Min(1)
  private int rows;

  @Min(1)
  private int seatsInRow;

  @Size(max = 200)
  private String description;
}
