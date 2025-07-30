package cinema.service.impl;

import cinema.dao.MovieSessionDao;
import cinema.model.MovieSession;
import cinema.service.MovieSessionService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MovieSessionServiceImpl implements MovieSessionService {
  private final MovieSessionDao movieSessionDao;

  public MovieSessionServiceImpl(MovieSessionDao movieSessionDao) {
    this.movieSessionDao = movieSessionDao;
  }

  @Override
  public List<MovieSession> findAvailableSessions(Long movieId, LocalDateTime date) {
    return movieSessionDao.findByMovieIdAndShowTimeAfter(movieId, date);
  }

  @Override
  public MovieSession add(MovieSession movieSession) {
    return movieSessionDao.save(movieSession);
  }

  @Override
  public MovieSession get(Long id) {
    return movieSessionDao
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Session with id " + id + " not found"));
  }

  @Override
  public MovieSession update(MovieSession movieSession) {
    return movieSessionDao.save(movieSession);
  }

  @Override
  public void delete(Long id) {
    movieSessionDao.deleteById(id);
  }
}
