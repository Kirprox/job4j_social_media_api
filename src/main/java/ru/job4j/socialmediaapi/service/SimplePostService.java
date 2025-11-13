package ru.job4j.socialmediaapi.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.job4j.socialmediaapi.dto.FileDto;
import ru.job4j.socialmediaapi.model.File;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.repository.PostRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final FileService fileService;

    @Transactional
    @Override
    public Post save(Post post, FileDto image) {
        File resultFile = (image != null) ? fileService.save(image) : null;
        LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        Post newPost = new Post(post.getTitle(), post.getText(),
                created, post.getUser(), resultFile);
        return postRepository.save(newPost);
    }

    @Transactional
    @Override
    public Post update(Post updatedPost, FileDto image) {
        Post currentPost = postRepository.findById(updatedPost.getId())
                .orElseThrow(() -> new IllegalArgumentException("Пост с id=" + updatedPost.getId() + " не найден"));
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

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll(Pageable.unpaged()).getContent();
    }
}
