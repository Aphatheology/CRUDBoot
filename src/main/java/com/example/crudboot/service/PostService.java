package com.example.crudboot.service;

import com.example.crudboot.dto.PostDto;
import com.example.crudboot.entity.Posts;
import com.example.crudboot.entity.Users;
import com.example.crudboot.exception.ResourceNotFoundException;
import com.example.crudboot.repository.PostRepository;
import com.example.crudboot.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final CommentService commentService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostService(CommentService commentService, PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public PostDto map2Dto(Posts post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setBody(post.getBody());
        postDto.setCategory(post.getCategory());
        postDto.setComments(post.getComments().isEmpty() ? new ArrayList<>() : this.commentService.mapCommentsToDto(post.getComments()));
        postDto.setPostedBy(post.getPostedBy().getUsername());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());

        return postDto;
    }



    public List<PostDto> getAllPosts() {
        List<Posts> posts = postRepository.findAll();

        return posts.stream()
                .map(this::map2Dto).toList();
    }

    public PostDto createPost(PostDto postBody) {
        Users postedBy = userRepository.findUserByUsername(postBody.getPostedBy()).orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + postBody.getPostedBy()));

        Posts post = new Posts();
        post.setTitle(postBody.getTitle());
        post.setCategory(postBody.getCategory());
        post.setBody(postBody.getBody());
        post.setPostedBy(postedBy);

        postRepository.save(post);

        return map2Dto(post);
    }

    public PostDto getPost(Long postId) {
        Posts post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post Not Found"));

        return map2Dto(post);
    }

    public PostDto updatePost(Long postId, PostDto postBody) {
        Posts post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post Not Found"));

        modelMapper.map(postBody, post);
        postRepository.save(post);
        return map2Dto(post);
    }

    public void deletePost(Long postId) {
        Posts post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post Not Found"));
        postRepository.delete(post);
    }
}
