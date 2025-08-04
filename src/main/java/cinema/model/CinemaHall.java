package cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "cinema_halls")
public class CinemaHall {
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "cinema_rows", nullable = false)
  private int rows;

  private int seatsInRow;
  private int capacity;
  @Setter
  private String description;

  public CinemaHall() {}

  public CinemaHall(int rows, int seatsInRow) {
    this.rows = rows;
    this.seatsInRow = seatsInRow;
    capacity = rows * seatsInRow;
  }

    @Override
  public String toString() {
    return "CinemaHall{"
        + "id="
        + id
        + ", capacity="
        + capacity
        + ", description='"
        + description
        + '\''
        + '}';
  }
}
