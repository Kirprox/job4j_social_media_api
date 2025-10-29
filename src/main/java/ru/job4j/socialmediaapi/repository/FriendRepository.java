package ru.job4j.socialmediaapi.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.socialmediaapi.model.Friend;

public interface FriendRepository extends CrudRepository<Friend, Long> {
}
