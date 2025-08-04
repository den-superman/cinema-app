package cinema.controller;

import cinema.dto.response.CommentResponseDto;
import cinema.model.Comment;
import cinema.service.CommentService;
import cinema.service.mapper.ResponseDtoMapper;
import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies/{movieId}/comments")
public class CommentController {
  private final CommentService commentService;
  private final ResponseDtoMapper<CommentResponseDto, Comment> commentResponseDtoMapper;

  public CommentController(
      CommentService commentService, ResponseDtoMapper<CommentResponseDto, Comment> mapper) {
    this.commentService = commentService;
    this.commentResponseDtoMapper = mapper;
  }

  @GetMapping("/accepted")
  public List<CommentResponseDto> showAcceptedComments(@PathVariable Long movieId) {
    return commentService.getAcceptedByMovieId(movieId).stream()
        .map(commentResponseDtoMapper::mapToDto)
        .toList();
  }

  @GetMapping("/pending")
  @PreAuthorize("hasRole('ADMIN')")
  public List<CommentResponseDto> showPendingComments(@PathVariable Long movieId) {
    return commentService.getPendingByMovieId(movieId).stream()
        .map(commentResponseDtoMapper::mapToDto)
        .toList();
  }

  @PostMapping
  public CommentResponseDto add(@PathVariable Long movieId, @RequestBody String text) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    Comment comment = commentService.add(text, movieId, username);

    return commentResponseDtoMapper.mapToDto(comment);
  }

  @PatchMapping("/{commentId}")
  public CommentResponseDto updateCommentStatus(
      @PathVariable Long commentId, @RequestBody Map<String, Comment.Status> statusUpdate) {
    Comment comment = commentService.get(commentId);
    comment.setStatus(statusUpdate.get("status"));
    return commentResponseDtoMapper.mapToDto(commentService.update(comment));
  }

  @DeleteMapping("/{commentId}")
  public void deleteComment(@PathVariable Long commentId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    commentService.delete(commentId, authentication);
  }
}
