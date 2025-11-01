package ru.job4j.socialmediaapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmediaapi.model.File;
import ru.job4j.socialmediaapi.model.Post;
import ru.job4j.socialmediaapi.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    private User user;
    private File file;

    @BeforeEach
    public void setupDb() {
        postRepository.deleteAll();
        friendRepository.deleteAll();
        subscriberRepository.deleteAll();
        fileRepository.deleteAll();
        userRepository.deleteAll();

        user = new User();
        user.setFullName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("12345");
        userRepository.save(user);

        file = new File();
        file.setName("picture1");
        file.setPath("/one");
        fileRepository.save(file);
    }

    @Test
    public void whenSavePostThenFindById() {
        var post = new Post();
        post.setTitle("Title1");
        post.setText("Text1");
        post.setCreated(LocalDateTime.now());
        post.setUser(user);
        post.setFile(file);

        postRepository.save(post);

        var found = postRepository.findById(post.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Title1");
        assertThat(found.get().getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    public void whenSavePostThenUpdatePostHasSameId() {
        var post = new Post();
        post.setTitle("Title1");
        post.setText("Text1");
        post.setCreated(LocalDateTime.now());
        post.setUser(user);
        post.setFile(file);
        postRepository.save(post);

        post.setTitle("Updated Title");
        postRepository.save(post);

        var found = postRepository.findById(post.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void whenFindAllThenReturnAllPosts() {
        var p1 = new Post(null, "Title1", "Text1", LocalDateTime.now(), user, file);
        var p2 = new Post(null, "Title2", "Text2", LocalDateTime.now(), user, file);
        postRepository.save(p1);
        postRepository.save(p2);

        var posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::getTitle).contains("Title1", "Title2");
    }
}