package cinema.controller;

import cinema.dto.response.CommentResponseDto;
import cinema.model.Comment;
import cinema.service.CommentService;
import cinema.service.MovieService;
import cinema.service.UserService;
import cinema.service.mapper.ResponseDtoMapper;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies/{movieId}/comments")
public class CommentController {
  private final CommentService commentService;
  private final ResponseDtoMapper<CommentResponseDto, Comment> mapper;

  public CommentController(
      CommentService commentService,
      UserService userService,
      ResponseDtoMapper<CommentResponseDto, Comment> mapper) {
    this.commentService = commentService;
    this.mapper = mapper;
  }

  @GetMapping("/accepted")
  public List<CommentResponseDto> showAcceptedComments(@PathVariable Long movieId, Model model) {
    return commentService.getAcceptedByMovieId(movieId).stream()
            .map(mapper::mapToDto).toList();
  }

  @GetMapping("/pending")
  @PreAuthorize("hasRole('ADMIN')")
  public List<CommentResponseDto> showPendingComments(@PathVariable Long movieId, Model model) {
    return commentService.getPendingByMovieId(movieId).stream()
            .map(mapper::mapToDto).toList();
  }

  @PostMapping
  public CommentResponseDto add(@PathVariable Long movieId, @RequestBody String text) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    Comment comment = commentService.add(text, movieId, username);

    return mapper.mapToDto(comment);
  }
}
