package ru.job4j.socialmediaapi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.job4j.socialmediaapi.model.Friend;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FriendRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;

    @BeforeEach
    public void cleanDb() {
        friendRepository.deleteAll();
    }

    @Test
    public void whenSaveFriendThenFindById() {
        var friend = new Friend();
        friend.setUser1Id(1);
        friend.setUser2Id(2);
        friend.setCreatedAt(LocalDateTime.now());
        friendRepository.save(friend);
        var found = friendRepository.findById(friend.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getUser1Id()).isEqualTo(1);
        assertThat(found.get().getUser2Id()).isEqualTo(2);
    }

    @Test
    public void whenSaveFriendThenUpdateFriendHasSameId() {
        var friend = new Friend();
        friend.setUser1Id(1);
        friend.setUser2Id(2);
        friend.setCreatedAt(LocalDateTime.now());
        friendRepository.save(friend);
        friend.setUser2Id(3);
        friendRepository.save(friend);
        var found = friendRepository.findById(friend.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getUser2Id()).isEqualTo(3);
    }

    @Test
    public void whenFindAllThenReturnAllFriends() {
        var f1 = new Friend(null, 1, 2, LocalDateTime.now());
        var f2 = new Friend(null, 2, 3, LocalDateTime.now());
        friendRepository.save(f1);
        friendRepository.save(f2);
        var friends = friendRepository.findAll();
        assertThat(friends).hasSize(2);
        assertThat(friends).extracting(Friend::getUser2Id).contains(2, 3);
    }
}