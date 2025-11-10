package ru.job4j.socialmediaapi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends CrudRepository<Friend, Long> {
    @Query("""
            SELECT friend FROM Friend friend
            WHERE friend.userA.id = :id or friend.userB.id = :id""")
    List<Friend> findFriendsByUserId(@Param("id")Long userId);

    @Query("""
            SELECT friend FROM Friend friend
            WHERE (friend.userA.id = :id1 AND friend.userB.id = :id2) 
            OR    (friend.userA.id = :id2 AND friend.userB.id = :id1)
            """)
    Optional<Friend> findByCurrentUserAndTargetUser(@Param("id1") Long currentUser,
                                                    @Param("id2") Long targetUser);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM Friend friend
            WHERE (friend.userA.id = :id1 AND friend.userB.id = :id2) 
            OR    (friend.userA.id = :id2 AND friend.userB.id = :id1)
            """)
    void deleteByCurrentUserAndTargetUser(@Param("id1") Long currentUser, @Param("id2") Long targetUser);
}
