package cinema.service.impl;

import cinema.dao.CommentDao;
import cinema.dto.response.CommentResponseDto;
import cinema.model.Comment;
import cinema.service.CommentService;
import cinema.service.mapper.ResponseDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao, ResponseDtoMapper<CommentResponseDto, Comment> mapper) {
        this.commentDao = commentDao;
    }

    @Override
    public Comment add(Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    public Comment get(Long id) {
        return commentDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't get comment by id " + id));
    }

    @Override
    public Comment update(Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    public void delete(Long id) {
        commentDao.deleteById(id);
    }

    @Override
    public List<Comment> getByMovieId(Long movieId) {
        return commentDao.findByMovieId(movieId);
    }
}
