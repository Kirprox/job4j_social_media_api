package ru.job4j.socialmediaapi.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmediaapi.model.Friend;
import ru.job4j.socialmediaapi.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FriendRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private FileRepository fileRepository;

    private User user1;
    private User user2;
    private User user3;

    @BeforeAll
    void initUsers() {
        user1 = new User();
        user1.setEmail("user1@mail.com");
        user1.setPassword("pass1");
        user1.setFullName("User1");
        userRepository.save(user1);

        user2 = new User();
        user2.setEmail("user2@mail.com");
        user2.setPassword("pass2");
        user2.setFullName("User2");
        userRepository.save(user2);

        user3 = new User();
        user3.setEmail("user3@mail.com");
        user3.setPassword("pass3");
        user3.setFullName("User3");
        userRepository.save(user3);
    }

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        friendRepository.deleteAll();
        subscriberRepository.deleteAll();
        fileRepository.deleteAll();
        userRepository.deleteAll();

        user1 = userRepository.save(new User(null, "User1", "user1@mail.com", "pass1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        user2 = userRepository.save(new User(null, "User2", "user2@mail.com", "pass2", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
        user3 = userRepository.save(new User(null, "User3", "user3@mail.com", "pass3", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    public void whenSaveFriendThenFindById() {
        var friend = new Friend();
        friend.setUserA(user1);
        friend.setUserB(user2);
        friend.setCreatedAt(LocalDateTime.now());
        friendRepository.save(friend);

        var found = friendRepository.findById(friend.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getUserA().getId()).isEqualTo(user1.getId());
        assertThat(found.get().getUserB().getId()).isEqualTo(user2.getId());
    }

    @Test
    public void whenSaveFriendThenUpdateFriendHasSameId() {
        var friend = new Friend();
        friend.setUserA(user1);
        friend.setUserB(user2);
        friend.setCreatedAt(LocalDateTime.now());
        friendRepository.save(friend);

        // обновляем user2
        friend.setUserB(user3);
        friendRepository.save(friend);

        var found = friendRepository.findById(friend.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getUserB().getId()).isEqualTo(user3.getId());
    }

    @Test
    public void whenFindAllThenReturnAllFriends() {
        var f1 = new Friend(null, user1, user2, LocalDateTime.now());
        var f2 = new Friend(null, user2, user3, LocalDateTime.now());
        friendRepository.save(f1);
        friendRepository.save(f2);

        var friends = friendRepository.findAll();
        assertThat(friends).hasSize(2);
        assertThat(friends)
                .extracting(f -> f.getUserB().getId())
                .contains(user2.getId(), user3.getId());
    }
}