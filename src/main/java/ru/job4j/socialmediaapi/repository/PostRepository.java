package ru.job4j.socialmediaapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUser(User user);

    List<Post> findByCreatedBetween(LocalDateTime start, LocalDateTime end);

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findById(Long id);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Post post SET post.title = :title,
                                 post.text = :text
            WHERE post.id = :id""")
    int updateTitleAndText(@Param("title") String newTitle,
                           @Param("text") String newText, @Param("id") Long postId);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Post post
            SET post.file = null
            WHERE post.id = :id""")
    int detachFileFromPost(@Param("id") Long postId);

    @Modifying(clearAutomatically = true)
    @Query("""
            DELETE From Post post
            WHERE post.id = :pId""")
    int deletePostById(@Param("pId") Long postId);

    @Query("""
            SELECT post FROM Post as post
            WHERE post.user.id IN (
                SELECT sub.subscriber.id
                FROM Subscriber as sub
                WHERE sub.subscribedTo = :id
                )
            ORDER BY post.created DESC""")
    Page<Post> findPostsOfSubscriptions(@Param("id") Long userId, Pageable pageable);
}
