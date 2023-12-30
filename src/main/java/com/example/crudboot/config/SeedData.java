package com.example.crudboot.config;

import com.example.crudboot.dto.CommentDto;
import com.example.crudboot.dto.PostDto;
import com.example.crudboot.dto.UserDto;
import com.example.crudboot.service.CommentService;
import com.example.crudboot.service.PostService;
import com.example.crudboot.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    public SeedData(UserService userService, PostService postService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<UserDto> usersDto = this.userService.getAllUsers();

//        if (usersDto.isEmpty()) {
//            UserDto userLagbaja = UserDto.builder()
//                    .email("lagbaja@gmail.com")
//                    .username("Lagbaja")
//                    .fullname("Lagbaja Person")
//                    .password("lagbajA123")
//                    .role("ADMIN")
//                    .build();
//
//            UserDto userTamedo = UserDto.builder()
//                    .email("tamedo@gmail.com")
//                    .username("Tamedo")
//                    .fullname("Tamedo Person")
//                    .password("tamedO123")
//                    .role("USER")
//                    .build();
//
//            this.userService.createUser(userLagbaja);
//            this.userService.createUser(userTamedo);
//
//            PostDto postByLagbaja = PostDto.builder()
//                    .postedBy("Lagbaja")
//                    .body("Post body")
//                    .title("Startup Lagbaja raise preseed")
//                    .category("Startup")
//                    .build();
//
//            PostDto post = this.postService.createPost(postByLagbaja);
//
//            CommentDto commentByTamedo = CommentDto.builder()
//                    .message("Comment on Lagbaja post by Tamedo")
//                    .by("Tamedo")
//                    .build();
//
//            this.commentService.createComment(post.getId(), commentByTamedo);
//        }
    }
}


