package cinema.service;

import cinema.model.MovieSession;
import java.time.LocalDateTime;
import java.util.List;

public interface MovieSessionService {
  List<MovieSession> findAvailableSessions(Long movieId, LocalDateTime date);

  MovieSession add(MovieSession session);

  MovieSession get(Long id);

  MovieSession update(MovieSession movieSession);

  void delete(Long id);
}
