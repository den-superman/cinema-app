package cinema.service;

import cinema.model.CinemaHall;
import cinema.model.Comment;
import cinema.model.MovieSession;

import java.util.List;

public interface CommentService {
    Comment add(Comment cinemaHall);

    Comment get(Long id);

    Comment update(Comment movieSession);

    void delete(Long id);

    List<Comment> getByMovieId(Long movieId);
}
