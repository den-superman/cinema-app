package cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany
  @JoinTable(
      name = "orders_tickets",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "ticket_id"))
  private List<Ticket> tickets;

  @Column(name = "order_time")
  private LocalDateTime orderTime;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

    @Override
  public String toString() {
    return "Order{"
        + "id="
        + id
        + ", tickets="
        + tickets
        + ", orderTime="
        + orderTime
        + ", user="
        + user
        + '}';
  }
}
