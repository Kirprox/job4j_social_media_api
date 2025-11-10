package ru.job4j.socialmediaapi.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.Subscriber;

import java.util.List;
import java.util.Optional;

public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {
    @Query("""
            SELECT sub from Subscriber sub
            WHERE sub.subscribedTo.id = :id""")
    List<Subscriber> findAllSubscribersByUserId(@Param("id") Long userId);

    @Query("""
            SELECT sub from Subscriber sub
            WHERE sub.subscriber.id = :id1 AND sub.subscribedTo.id = :id2
            """)
    Optional<Subscriber> findBySubscriberAndSubscribedTo(@Param("id1")Long subscriber,
                                                         @Param("id2")Long subscribedToId);

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM Subscriber sub
            WHERE sub.subscriber.id = :id1 AND sub.subscribedTo.id = :id2
            """)
    void deleteBySubscriberAndSubscribedToId(@Param("id1")Long subscriber,
                                             @Param("id2")Long subscribedToId);
}
