package cinema.dao;

import cinema.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {
  List<Comment> findByMovieIdAndStatus(Long movieId, Comment.Status status);
}
