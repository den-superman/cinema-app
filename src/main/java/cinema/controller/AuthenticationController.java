package cinema.controller;

import cinema.dto.request.UserRequestDto;
import cinema.dto.response.UserResponseDto;
import cinema.model.User;
import cinema.service.AuthenticationService;
import cinema.service.mapper.ResponseDtoMapper;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AuthenticationController {
    private final AuthenticationService authService;
    private final ResponseDtoMapper<UserResponseDto, User> userDtoResponseMapper;

    public AuthenticationController(AuthenticationService authService,
            ResponseDtoMapper<UserResponseDto, User> userDtoResponseMapper) {
        this.authService = authService;
        this.userDtoResponseMapper = userDtoResponseMapper;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
//        model.addAttribute("userRequestDto", new UserRequestDto());
        return "login";
    }



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid UserRequestDto requestDto, BindingResult result, Model model) {
        User user = authService.register(requestDto.getEmail(), requestDto.getPassword());
        model.addAttribute("message", "Registration successful! Please log in.");
        return "result";
    }
}
