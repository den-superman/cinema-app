package cinema.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CinemaHallResponseDto {
  private Long id;
  private int capacity;
  private String description;
}
