package com.example.crudboot.service;

import com.example.crudboot.dto.PostDto;
import com.example.crudboot.entity.Posts;
import com.example.crudboot.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

//    @BeforeEach
//    void setUp() {
//        Post post = Post.builder()
//                .title("Learn Servlet the easy way")
//                .author("Aphatheology")
//                .yop("2023")
//                .id(2L)
//                .build();
//
//        Mockito.when(postRepository.findById(2L)).thenReturn(Optional.ofNullable(post));
//    }

    @Test
    @DisplayName("Get post for valid id")
    public void whenValidPostId_thenPostShouldFound() {
        Long postId = 2L;
        PostDto postFound = postService.getPost(postId);

        assertEquals(postId, postFound.getId());
    }

}