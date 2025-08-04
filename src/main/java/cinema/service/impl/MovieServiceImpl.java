package cinema.service.impl;

import cinema.dao.MovieDao;
import cinema.model.Movie;
import cinema.service.MovieService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
  private final MovieDao movieDao;

  public MovieServiceImpl(MovieDao movieDao) {
    this.movieDao = movieDao;
  }

  @Override
  public Movie add(Movie movie) {
    return movieDao.save(movie);
  }

  @Override
  public Movie get(Long id) {
    return movieDao
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Can't get movie by id " + id));
  }

  @Override
  public List<Movie> getAll() {
    return movieDao.findAll();
  }
}
