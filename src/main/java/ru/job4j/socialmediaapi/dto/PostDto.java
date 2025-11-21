package ru.job4j.socialmediaapi.dto;

import lombok.Data;
import ru.job4j.socialmediaapi.model.Post;

import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String userName;
    private List<Post> posts;

    public PostDto(Long id, String userName, List<Post> posts) {
        this.id = id;
        this.userName = userName;
        this.posts = posts;
    }
}
