package ru.job4j.socialmediaapi.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.model.File;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.repository.PostRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Service
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final FileService fileService;

    @Transactional
    @Override
    public Post save(Post post, FileDto image) {
        File resultFile = fileService.save(image);
        LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        Post newPost = new Post(post.getTitle(), post.getText(),
                created, post.getUser(), resultFile);
        return postRepository.save(newPost);
    }

    @Override
    public Post update(Long postId, Post updatedPost, FileDto image) {
        Post currentPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Пост с id=" + postId + " не найден"));
        currentPost.setTitle(updatedPost.getTitle());
        currentPost.setText(updatedPost.getText());
        if (image != null) {
            currentPost.setFile(fileService.save(image));
        }
        return postRepository.save(currentPost);
    }

    @Transactional
    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пост с id=" + id + " не найден"));
        postRepository.delete(post);
    }
}
