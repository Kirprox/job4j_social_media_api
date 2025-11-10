package ru.job4j.socialmediaapi.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmediaapi.model.Friend;
import ru.job4j.socialmediaapi.model.Subscriber;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.repository.FriendRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleFriendService implements FriendService {
    private final FriendRepository friendRepository;
    private final SubscribeService subscribeService;

    @Transactional
    @Override
    public Optional<Friend> save(User currentUser, User targetUser) {
        Friend resultFriend = null;
        Subscriber resultSubscriber = null;
        var friend = friendRepository.findByCurrentUserAndTargetUser(currentUser.getId(), targetUser.getId());
        if (friend.isPresent()) {
            resultFriend = friend.get();
        } else {
            var subscriber = subscribeService.findBySubscriberAndSubscribedTo(currentUser.getId(), targetUser.getId());
            var mutualSubscriber = subscribeService.findBySubscriberAndSubscribedTo(targetUser.getId(), currentUser.getId());
            var created = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            if (subscriber.isPresent() && mutualSubscriber.isPresent()) {
                resultFriend = new Friend(currentUser, targetUser, created);
                resultFriend = friendRepository.save(resultFriend);
            } else {
                resultSubscriber = new Subscriber(currentUser, targetUser, created);
                subscribeService.save(resultSubscriber);
            }
        }
        return Optional.ofNullable(resultFriend);
    }

    @Transactional
    @Override
    public void deleteFriendByCurrentUserAndTargetUser(Long currentUser, Long targetUser) {
        Optional<Friend> friend = friendRepository.findByCurrentUserAndTargetUser(currentUser, targetUser);
        if (friend.isPresent()) {
            friendRepository.deleteByCurrentUserAndTargetUser(currentUser, targetUser);
            subscribeService.deleteSubscriberByCurrentUserAndTargetUser(currentUser, targetUser);
        }
    }
}
