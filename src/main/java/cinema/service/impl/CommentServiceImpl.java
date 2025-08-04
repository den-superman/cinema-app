package cinema.service.impl;

import static cinema.model.Comment.Status.ACCEPTED;
import static cinema.model.Comment.Status.PENDING;

import cinema.dao.CommentDao;
import cinema.dto.request.OpenAiRequestDto;
import cinema.dto.response.OpenAiResponseDto;
import cinema.model.Comment;
import cinema.service.CommentService;
import cinema.service.MovieService;
import cinema.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CommentServiceImpl implements CommentService {
  public static final String MODERATION_PROMPT =
      """
           You are a comment moderator.
           You review user comments and decide if they are appropriate to publish.
           If the comment is inappropriate, offensive, toxic, or spam — respond with 'DENIED'.
           If the context is unclear or the comment appears harmless but anomalous
           (e.g. off-topic, incoherent, or suspicious), respond with 'DENIED'.
           The comment will then be flagged for manual review by a human moderator.
           If the comment is short or generic but harmless (e.g. "cool", "nice", "thanks") — respond with 'ACCEPTED'.
           If unsure, prefer 'DENIED' for human review.
           If it's okay, respond with 'ACCEPTED'. Reply strictly with one word: 'ACCEPTED' or 'DENIED'.
          """;
  private final CommentDao commentDao;
  private final WebClient webClient;
  private final MovieService movieService;
  private final UserService userService;

  public CommentServiceImpl(
      CommentDao commentDao,
      @Value("${openai.api.key}") String apiKey,
      MovieService movieService,
      UserService userService) {
    this.commentDao = commentDao;
    this.webClient =
        WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .defaultHeader("Authorization", "Bearer " + apiKey)
            .defaultHeader("Content-Type", "application/json")
            .build();
    this.movieService = movieService;
    this.userService = userService;
  }

  @Override
  public Comment add(Comment comment) {
    return commentDao.save(comment);
  }

  private boolean isValidContent(String comment) {
    OpenAiRequestDto request = new OpenAiRequestDto();
    request.setModel("deepseek/deepseek-r1:free");
    request.setTemperature(0);

    List<OpenAiRequestDto.Message> messages =
        List.of(
            new OpenAiRequestDto.Message("system", MODERATION_PROMPT),
            new OpenAiRequestDto.Message("user", comment));

    request.setMessages(messages);

    OpenAiResponseDto response =
        webClient
            .post()
            .uri("https://openrouter.ai/api/v1/chat/completions")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(OpenAiResponseDto.class)
            .block();

    String result = response.getChoices().get(0).getMessage().getContent().trim();

    return result.equals("ACCEPTED");
  }

  @Override
  public Comment add(String commentText, Long movieId, String username) {
    Comment comment = new Comment();
    comment.setMovie(movieService.get(movieId));
    comment.setUser(
        userService
            .findByEmail(username)
            .orElseThrow(() -> new RuntimeException("Unknown user writes comment")));
    comment.setText(commentText);
    comment.setStatus(isValidContent(commentText) ? ACCEPTED : PENDING);

    return commentDao.save(comment);
  }

  @Override
  public Comment get(Long id) {
    return commentDao
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Can't get comment by id " + id));
  }

  @Override
  public Comment update(Comment comment) {
    return commentDao.save(comment);
  }

  @Override
  public void delete(Long commentId, Authentication auth) {
    Comment comment =
        commentDao.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

    boolean isAdmin =
        auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    if (isAdmin || comment.getUser().getEmail().equals(auth.getName())) {
      commentDao.deleteById(commentId);
    } else {
      throw new RuntimeException("You don't have permission to delete this comment");
    }
  }

  @Override
  public List<Comment> getAcceptedByMovieId(Long movieId) {
    return commentDao.findByMovieIdAndStatus(movieId, ACCEPTED);
  }

  @Override
  public List<Comment> getPendingByMovieId(Long movieId) {
    return commentDao.findByMovieIdAndStatus(movieId, PENDING);
  }
}
