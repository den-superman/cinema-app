package cinema.controller;

import cinema.dto.request.UserRequestDto;
import cinema.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
  private final AuthenticationService authService;

  public AuthenticationController(AuthenticationService authService) {
    this.authService = authService;
  }

  @GetMapping("/login")
  public String showLoginForm() {
    return "login";
  }

  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("userRequestDto", new UserRequestDto());
    return "register";
  }

  @PostMapping("/register")
  public String register(
      @Valid @ModelAttribute UserRequestDto dto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "register";
    }

    authService.register(dto.getEmail(), dto.getPassword());
    model.addAttribute("message", "Registration successful! Please log in");
    return "login";
  }
}
