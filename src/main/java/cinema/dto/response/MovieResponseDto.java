package cinema.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovieResponseDto {
  private Long id;
  private String title;
  private String description;
}
