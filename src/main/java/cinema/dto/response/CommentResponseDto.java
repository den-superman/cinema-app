package cinema.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseDto {
  private Long id;
  private String userEmail;
  private String text;
}
