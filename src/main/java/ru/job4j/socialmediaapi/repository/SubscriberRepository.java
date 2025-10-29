package ru.job4j.socialmediaapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.socialmediaapi.model.Subscriber;

public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {
}
