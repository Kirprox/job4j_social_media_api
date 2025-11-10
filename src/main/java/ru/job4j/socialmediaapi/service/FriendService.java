package ru.job4j.socialmediaapi.service;

import ru.job4j.socialmediaapi.model.Friend;
import ru.job4j.socialmediaapi.model.User;

import java.util.Optional;

public interface FriendService {
    Optional<Friend> save(User currentUser, User targetUser);

    void deleteFriendByCurrentUserAndTargetUser(Long currentUser, Long targetUser);
}
