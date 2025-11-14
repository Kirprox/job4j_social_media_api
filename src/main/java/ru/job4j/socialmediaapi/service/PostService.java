package ru.job4j.socialmediaapi.service;

import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post save(Post post, FileDto image);

    boolean update(Post updatedPost, FileDto image);

    boolean deletePostById(Long id);

    Optional<Post> findById(Long id);

    List<Post> findAll();
}
