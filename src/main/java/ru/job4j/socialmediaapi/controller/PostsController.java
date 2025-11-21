package ru.job4j.socialmediaapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmediaapi.dto.PostDto;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.service.PostService;
import ru.job4j.socialmediaapi.service.UserService;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        List<Post> posts = postService.findAll();
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/by_users_id")
    public List<PostDto> getAllByUsersId(@RequestBody List<Long> idList) {
        List<PostDto> result = new ArrayList<>();
        for (Long id : idList) {
            User currentUser = userService.findById(id).get();
            List<Post> posts = postService.findPostsByUserId(id);
            result.add(new PostDto(id, currentUser.getFullName(), posts));
        }
        return result;
    }
}
