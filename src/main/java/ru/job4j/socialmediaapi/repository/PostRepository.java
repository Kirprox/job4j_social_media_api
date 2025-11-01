package ru.job4j.socialmediaapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUser(User user);

    List<Post> findByCreatedBetween(LocalDateTime start, LocalDateTime end);

    Page<Post> findAll(Pageable pageable);
}
