package cinema.controller;

import cinema.dto.response.CommentResponseDto;
import cinema.model.Comment;
import cinema.model.Movie;
import cinema.service.CommentService;
import cinema.service.mapper.ResponseDtoMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/comments")
public class CommentController {
    private final CommentService commentService;
    private final ResponseDtoMapper<CommentResponseDto, Comment> mapper;

    public CommentController(CommentService commentService, ResponseDtoMapper<CommentResponseDto, Comment> mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<CommentResponseDto> showComment(@PathVariable Long movieId, Model model) {
        return commentService.getByMovieId(movieId)
                .stream().map(mapper::mapToDto).toList();
    }
}
