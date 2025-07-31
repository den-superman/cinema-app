package cinema.service.mapper;

import cinema.dto.response.CommentResponseDto;
import cinema.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper implements ResponseDtoMapper<CommentResponseDto, Comment> {

  @Override
  public CommentResponseDto mapToDto(Comment comment) {
    return new CommentResponseDto(comment.getId(), comment.getUser().getEmail(), comment.getText());
  }
}
