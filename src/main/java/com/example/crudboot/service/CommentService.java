package com.example.crudboot.service;

import com.example.crudboot.dto.CommentDto;
import com.example.crudboot.entity.Comments;
import com.example.crudboot.entity.Posts;
import com.example.crudboot.entity.Users;
import com.example.crudboot.exception.ResourceNotFoundException;
import com.example.crudboot.repository.CommentRepository;
import com.example.crudboot.repository.PostRepository;
import com.example.crudboot.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public CommentDto map2Dto(Comments comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setMessage(comment.getMessage());
        commentDto.setPost(comment.getPost().getId());
        commentDto.setBy(comment.getBy().getUsername());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setUpdatedAt(comment.getUpdatedAt());

        return commentDto;
    }

    public List<CommentDto> mapCommentsToDto(List<Comments> comments) {
        return comments.stream()
                .map(this::map2Dto)
                .toList();
    }

    public List<CommentDto> getAllComments() {
        List<Comments> comments = commentRepository.findAll();

        return comments.stream().map(this::map2Dto).toList();
    }

    public CommentDto createComment(Long postId, CommentDto commentBody) {
        Posts post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post Not Found"));

        Users commentBy = userRepository.findUserByUsername(commentBody.getBy()).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + commentBody.getBy()));

        Comments comment = new Comments();
        comment.setMessage(commentBody.getMessage());
        comment.setPost(post);
        comment.setBy(commentBy);

        commentRepository.save(comment);

        return map2Dto(comment);
    }

    public CommentDto getComment(Long commentId) {
        Comments comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment Not Found"));

        return map2Dto(comment);
    }

    public CommentDto updateComment(Long commentId, CommentDto commentBody) {
        Comments comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment Not Found"));

        modelMapper.map(commentBody, comment);
        commentRepository.save(comment);
        return map2Dto(comment);
    }

    public void deleteComment(Long commentId) {
        Comments comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment Not Found"));
        commentRepository.delete(comment);
    }
}
