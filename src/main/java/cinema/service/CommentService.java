package cinema.service;

import cinema.model.Comment;
import java.util.List;

public interface CommentService {
  Comment add(Comment comment);

  Comment add(String commentText, Long movieId, String username);

  Comment get(Long id);

  Comment update(Comment comment);

  void delete(Long id);

  List<Comment> getAcceptedByMovieId(Long movieId);

  List<Comment> getPendingByMovieId(Long movieId);
}
