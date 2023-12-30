package com.example.crudboot.repository;

import com.example.crudboot.entity.Posts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager entityManager;

//    @BeforeEach
//    void setUp() {
//        Post post = Post.builder()
//                .title("Learn Servlet the easy way")
//                .author("Aphatheology")
//                .yop("2023")
//                .build();
//
//        entityManager.persist(post);
//    }

    @Test
    public void whenFindById_thenReturnPost() {
        Posts post = postRepository.findById(1L).get();
        assertEquals(post.getTitle(), "Learn Servlet the easy way");
    }
}