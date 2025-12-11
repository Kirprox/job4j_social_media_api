package ru.job4j.socialmediaapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmediaapi.model.User;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private FileRepository fileRepository;

    @BeforeEach
    public void cleanDb() {
        postRepository.deleteAll();
        friendRepository.deleteAll();
        subscriberRepository.deleteAll();
        fileRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void whenSaveUserThenFindById() {
        var user = new User();
        user.setUserName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password1234567");

        userRepository.save(user);
        var found = userRepository.findById(user.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getUserName()).isEqualTo("John Doe");
        assertThat(found.get().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    public void whenSaveUserThenUpdateUserHasSameId() {
        var user = new User();
        user.setUserName("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("password1234567");
        userRepository.save(user);

        user.setUserName("Alice Updated");
        userRepository.save(user);

        var found = userRepository.findById(user.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getUserName()).isEqualTo("Alice Updated");
    }

    @Test
    public void whenFindAllThenReturnAllUsers() {
        var u1 = new User();
        u1.setUserName("User1");
        u1.setEmail("u1@example.com");
        u1.setPassword("password1234567");
        var u2 = new User();
        u2.setUserName("User2");
        u2.setEmail("u2@example.com");
        u2.setPassword("password1234567");

        userRepository.save(u1);
        userRepository.save(u2);

        var users = userRepository.findAll();
        assertThat(users).hasSize(2);
    }
}