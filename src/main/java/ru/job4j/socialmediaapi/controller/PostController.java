package ru.job4j.socialmediaapi.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.service.PostService;

@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId")
                                    @NotNull
                                    @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                    Long postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post) {
        Post savedPost = postService.save(post, null);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(savedPost);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Post post) {
        if (postService.update(post, null)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@RequestBody Post post) {
        postService.update(post, null);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long postId) {
        if (postService.deletePostById(postId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
