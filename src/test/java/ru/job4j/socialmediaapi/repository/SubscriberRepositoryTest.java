package ru.job4j.socialmediaapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmediaapi.model.Subscriber;
import ru.job4j.socialmediaapi.model.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubscriberRepositoryTest {

    @Autowired
    private SubscriberRepository subscriberRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private FileRepository fileRepository;

    private User u1;
    private User u2;

    @BeforeEach
    public void setupDb() {
        postRepository.deleteAll();
        friendRepository.deleteAll();
        subscriberRepository.deleteAll();
        fileRepository.deleteAll();
        userRepository.deleteAll();

        u1 = new User();
        u1.setFullName("User One");
        u1.setEmail("user1@example.com");
        u1.setPassword("password1234567");

        u2 = new User();
        u2.setFullName("User Two");
        u2.setEmail("user2@example.com");
        u2.setPassword("password1234567");

        userRepository.save(u1);
        userRepository.save(u2);
    }

    @Test
    public void whenSaveSubscriberThenFindById() {
        var sub = new Subscriber();
        sub.setSubscriber(u1);
        sub.setSubscribedTo(u2);
        sub.setCreatedAt(LocalDateTime.now());
        subscriberRepository.save(sub);

        var found = subscriberRepository.findById(sub.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getSubscriber().getId()).isEqualTo(u1.getId());
        assertThat(found.get().getSubscribedTo().getId()).isEqualTo(u2.getId());
    }

    @Test
    public void whenSaveSubscriberThenUpdateSubscriberHasSameId() {
        var sub = new Subscriber();
        sub.setSubscriber(u1);
        sub.setSubscribedTo(u2);
        sub.setCreatedAt(LocalDateTime.now());
        subscriberRepository.save(sub);

        sub.setSubscribedTo(u1);
        subscriberRepository.save(sub);

        var found = subscriberRepository.findById(sub.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getSubscribedTo().getId()).isEqualTo(u1.getId());
    }

    @Test
    public void whenFindAllThenReturnAllSubscribers() {
        var s1 = new Subscriber(null, u1, u2, LocalDateTime.now());
        var s2 = new Subscriber(null, u2, u1, LocalDateTime.now());
        subscriberRepository.save(s1);
        subscriberRepository.save(s2);

        var subs = subscriberRepository.findAll();
        assertThat(subs).hasSize(2);
        assertThat(subs)
                .extracting(sub -> sub.getSubscribedTo().getId())
                .contains(u2.getId(), u1.getId());
    }
}