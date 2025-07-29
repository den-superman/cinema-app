package cinema.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class CinemaHallRequestDto {
  @Min(1)
  private int rows;

  @Min(1)
  private int seatsInRow;

  @Size(max = 200)
  private String description;

  public int getRows() {
    return rows;
  }

  public int getSeatsInRow() {
    return seatsInRow;
  }

  public String getDescription() {
    return description;
  }
}
