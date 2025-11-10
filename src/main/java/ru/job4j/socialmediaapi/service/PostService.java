package ru.job4j.socialmediaapi.service;

import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.model.Post;

public interface PostService {
    Post save(Post post, FileDto image);

    Post update(Long postId, Post updatedPost, FileDto image);

    void deletePostById(Long id);
}
