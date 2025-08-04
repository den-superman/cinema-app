package cinema.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "movies")
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;

    @Override
  public String toString() {
    return "Movie{"
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Movie movie = (Movie) o;
    return Objects.equals(id, movie.id)
        && Objects.equals(title, movie.title)
        && Objects.equals(description, movie.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description);
  }
}
