package cinema.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OrderResponseDto {
  private Long id;
  private List<Long> ticketIds;
  private Long userId;
  private LocalDateTime orderTime;

}
