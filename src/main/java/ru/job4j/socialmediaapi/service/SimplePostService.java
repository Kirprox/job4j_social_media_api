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
    public boolean update(Post updatedPost, FileDto image) {
        boolean result;
        Optional<Post> optionalPost = postRepository.findById(updatedPost.getId());
        if (optionalPost.isEmpty()) {
            result = false;
        } else {
            Post currentPost = optionalPost.get();
            if (image != null) {
                currentPost.setFile(fileService.save(image));
            }
            currentPost.setTitle(updatedPost.getTitle());
            currentPost.setText(updatedPost.getText());
            postRepository.save(currentPost);
            result = true;
        }
        return result;
    }

    @Transactional
    @Override
    public boolean deletePostById(Long id) {
        return postRepository.deletePostById(id) > 1;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll(Pageable.unpaged()).getContent();
    }

    @Override
    public List<Post> findPostsByUserId(Long id) {
        return postRepository.findByUserId(id);
    }
}
