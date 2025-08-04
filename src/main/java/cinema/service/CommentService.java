package cinema.service;

import cinema.model.Comment;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface CommentService {
  Comment add(Comment comment);

  Comment add(String commentText, Long movieId, String username);

  Comment get(Long commentId);

  Comment update(Comment comment);

  void delete(Long commentId, Authentication authentication);

  List<Comment> getAcceptedByMovieId(Long movieId);

  List<Comment> getPendingByMovieId(Long movieId);
}
