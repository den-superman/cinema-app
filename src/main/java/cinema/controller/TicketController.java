package cinema.controller;

import cinema.dao.TicketDao;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tickets")
public class TicketController {
  private final TicketDao ticketDao;

  public TicketController(TicketDao ticketDao) {
    this.ticketDao = ticketDao;
  }

  @GetMapping
  public String getTickets(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    model.addAttribute("tickets", ticketDao.findByUserEmailOrderByIdDesc(email));

    return "tickets";
  }
}
