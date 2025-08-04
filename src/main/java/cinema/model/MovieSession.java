package cinema.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "movie_sessions")
public class MovieSession {
  @Setter
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @Getter
  @ManyToOne
  @JoinColumn(name = "movie_id")
  private Movie movie;

  @Setter
  @Getter
  @ManyToOne
  @JoinColumn(name = "cinema_hall_id")
  private CinemaHall cinemaHall;

  @Setter
  @Getter
  @Column(name = "show_time")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime showTime;

  @Column(columnDefinition = "text") // или "text"
  private String occupiedSeatsJson; // Store the 2D array as JSON

  @Transient private boolean[][] occupiedSeats;

  public MovieSession() {}

  public MovieSession(int rows, int seatsInRow) {
    setOccupiedSeats(new boolean[rows][seatsInRow]);
  }

  // The actual 2D array (not mapped)

  // Convert 2D array to JSON when persisting
  public void setOccupiedSeats(boolean[][] occupiedSeats) {
    this.occupiedSeats = occupiedSeats;
    try {
      this.occupiedSeatsJson = new ObjectMapper().writeValueAsString(occupiedSeats);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize occupiedSeats", e);
    }
  }

  // Convert JSON back to 2D array when reading
  public boolean[][] getOccupiedSeats() {
    if (this.occupiedSeats == null && this.occupiedSeatsJson != null) {
      try {
        this.occupiedSeats =
            new ObjectMapper().readValue(this.occupiedSeatsJson, boolean[][].class);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Failed to deserialize occupiedSeatsJson", e);
      }
    }
    return this.occupiedSeats;
  }

    @Override
  public String toString() {
    return "MovieSession{"
        + "id="
        + id
        + ", movie="
        + movie
        + ", cinemaHall="
        + cinemaHall
        + ", showTime="
        + showTime
        + '}';
  }
}
