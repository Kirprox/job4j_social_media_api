package ru.job4j.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@Tag(name = "PostController", description = "PostController management APIs")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @Operation(
            summary = "Retrieve a Post by postId",
            description = "Get a Post object by specifying its postId. Returns postId, title, text, author and attached file.",
            tags = {"Post", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Post not found")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<Post> get(@PathVariable("postId")
                                    @NotNull
                                    @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                    Long postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new Post",
            description = "Create a new post. Returns the created Post object with generated ID.",
            tags = {"Post", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Post.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid post input")
    })
    @PostMapping
    public ResponseEntity<Post> save(@Valid @RequestBody Post post) {
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

    @Operation(
            summary = "Update an existing Post",
            description = "Update all details of an existing post. Returns 200 OK if successful, 404 if post not found.",
            tags = {"Post", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @ApiResponse(responseCode = "400", description = "Post not found")
    })
    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody Post post) {
        if (postService.update(post, null)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Partially update a Post",
            description = "Update some fields of an existing post. Returns 200 OK if successful, 404 if post not found.",
            tags = {"Post", "patch"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PatchMapping
    public ResponseEntity<Void> change(@Valid @RequestBody Post post) {
        if (postService.update(post, null)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Delete a Post by postId",
            description = "Delete an existing post by specifying its postId. Returns 204 No Content if successful, 404 if post not found.",
            tags = {"Post", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteById(@NotNull(message = "id не может быть null")
                                           @Min(value = 1, message = "id должен быть больше или равен 1")
                                           @PathVariable Long postId) {
        if (postService.deletePostById(postId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
