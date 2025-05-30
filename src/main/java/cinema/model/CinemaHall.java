package cinema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cinema_halls")
public class CinemaHall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cinema_rows", nullable = false)
    private int rows;
    private int seatsInRow;
    private int capacity;
    private String description;

    public CinemaHall() {
    }

    public CinemaHall(int rows, int seatsInRow) {
        this.rows = rows;
        this.seatsInRow = seatsInRow;
        capacity = rows * seatsInRow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRows() {
        return rows;
    }

    public int getSeatsInRow() {
        return seatsInRow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CinemaHall{"
                + "id=" + id
                + ", capacity=" + capacity
                + ", description='" + description + '\'' + '}';
    }
}
