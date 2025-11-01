package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.Friend;

import java.util.List;

public interface FriendRepository extends CrudRepository<Friend, Long> {
    @Query("""
            SELECT friend From Friend as friend
            WHERE friend.user1.id = :id or friend.user2.id = :id""")
    List<Friend> findFriendsByUserId(@Param("id")Long userId);
}
