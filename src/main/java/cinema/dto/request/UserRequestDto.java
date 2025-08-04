package cinema.dto.request;

import cinema.lib.FieldsValueMatch;
import cinema.lib.ValidEmail;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@FieldsValueMatch(
    field = "password",
    fieldMatch = "repeatPassword",
    message = "Passwords do not match!")
public class UserRequestDto {
  @ValidEmail private String email;

  @Size(min = 8, max = 40)
  private String password;

  private String repeatPassword;
}
