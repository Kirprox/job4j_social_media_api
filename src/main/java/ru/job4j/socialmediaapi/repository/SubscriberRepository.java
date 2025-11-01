package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.Subscriber;

import java.util.List;

public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {
    @Query("""
            SELECT sub from Subscriber as sub
            WHERE sub.subscribedTo.id = :id""")
    List<Subscriber> findSubscribersByUserId(@Param("id") Long userId);
}
