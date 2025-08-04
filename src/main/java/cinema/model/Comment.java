package cinema.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "movie_id")
  private Movie movie;

  public enum Status {
    ACCEPTED,
    PENDING,
    REJECTED,
    DELETED
  }
}
