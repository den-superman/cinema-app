package cinema.controller;

import cinema.dto.response.CommentResponseDto;
import cinema.model.Comment;
import cinema.service.CommentService;
import cinema.service.MovieService;
import cinema.service.UserService;
import cinema.service.mapper.ResponseDtoMapper;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies/{movieId}/comments")
public class CommentController {
  private final CommentService commentService;
  private final MovieService movieService;
  private final UserService userService;
  private final ResponseDtoMapper<CommentResponseDto, Comment> mapper;

  public CommentController(
      CommentService commentService,
      MovieService movieService,
      UserService userService,
      ResponseDtoMapper<CommentResponseDto, Comment> mapper) {
    this.commentService = commentService;
    this.movieService = movieService;
    this.userService = userService;
    this.mapper = mapper;
  }

  @GetMapping
  public List<CommentResponseDto> showComment(@PathVariable Long movieId, Model model) {
    return commentService.getByMovieId(movieId).stream().map(mapper::mapToDto).toList();
  }

  @PostMapping
  public CommentResponseDto add(@PathVariable Long movieId, @RequestBody String text) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    Comment comment = new Comment();
    comment.setMovie(movieService.get(movieId));
    comment.setUser(
        userService
            .findByEmail(username)
            .orElseThrow(() -> new RuntimeException("Unknown user writes comment")));
    comment.setText(text);
    comment = commentService.add(comment);

    return mapper.mapToDto(comment);
  }
}
