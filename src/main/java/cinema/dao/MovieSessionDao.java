package cinema.dao;

import cinema.model.MovieSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieSessionDao extends JpaRepository<MovieSession, Long> {

  List<MovieSession> findByMovieIdAndShowTimeAfter(Long movieId, LocalDateTime dateTime);
}
