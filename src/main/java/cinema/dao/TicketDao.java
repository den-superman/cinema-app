package cinema.dao;

import cinema.model.Ticket;
import cinema.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Long> {
  List<Ticket> findByUser(User user);

  List<Ticket> findByUserId(Long userId);

  List<Ticket> findByUserEmailOrderByIdDesc(String email);
}
