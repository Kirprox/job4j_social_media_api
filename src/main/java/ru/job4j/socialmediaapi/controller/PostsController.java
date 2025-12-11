package ru.job4j.socialmediaapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmediaapi.dto.PostDto;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.service.PostService;
import ru.job4j.socialmediaapi.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "PostsController", description = "PostsController management APIs")
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private final PostService postService;
    private final UserService userService;

    @Operation(
            summary = "Retrieve all posts",
            description = "Get a list of all posts. Returns 200 OK with posts or 204 No Content if no posts exist.",
            tags = {"Post", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "No posts found")
    })
    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        List<Post> posts = postService.findAll();
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(posts);
    }

    @Operation(
            summary = "Retrieve posts by a list of user IDs",
            description = "Get posts for multiple users by providing their IDs. Returns list of PostDto objects with user info and their posts.",
            tags = {"Post", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input list of user IDs")
    })
    @PostMapping("/by_users_id")
    public List<PostDto> getAllByUsersId(
            @RequestBody List<
                    @NotNull(message = "id не может быть null")
                    @Min(value = 1, message = "id должен быть больше или равен 1")
                            Long> idList) {
        List<PostDto> result = new ArrayList<>();
        for (Long id : idList) {
            User currentUser = userService.findById(id).get();
            List<Post> posts = postService.findPostsByUserId(id);
            result.add(new PostDto(id, currentUser.getUserName(), posts));
        }
        return result;
    }
}
