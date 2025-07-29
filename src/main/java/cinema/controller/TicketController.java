package cinema.controller;

import cinema.dao.TicketDao;
import cinema.model.User;
import cinema.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tickets")
public class TicketController {
  private final UserService userService;
  private final TicketDao ticketDao;

  public TicketController(UserService userService, TicketDao ticketDao) {
    this.userService = userService;
    this.ticketDao = ticketDao;
  }

  @GetMapping
  public String getTickets(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User user =
        userService
            .findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));

    model.addAttribute("tickets", ticketDao.findByUser(user));

    return "tickets";
  }
}
