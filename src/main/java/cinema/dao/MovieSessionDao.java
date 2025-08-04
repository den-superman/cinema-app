package cinema.dao;

import cinema.model.MovieSession;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieSessionDao extends JpaRepository<MovieSession, Long> {

  List<MovieSession> findByMovieIdAndShowTimeAfter(Long movieId, LocalDateTime dateTime);
}
