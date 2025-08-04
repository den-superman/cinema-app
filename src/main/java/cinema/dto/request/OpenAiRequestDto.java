package cinema.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OpenAiRequestDto {
  private String model;
  private double temperature;
  private double max_tokens;
  private List<Message> messages;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Message {
    private String role;
    private String content;
  }
}
