package ru.job4j.socialmediaapi.service;

import ru.job4j.socialmediaapi.model.Subscriber;

import java.util.List;
import java.util.Optional;

public interface SubscribeService {
    Subscriber save(Subscriber subscriber);

    Optional<Subscriber> findBySubscriberAndSubscribedTo(Long subscriberId, Long subscribedToId);

    List<Subscriber> findAllSubscribersByUserId(Long id);

    void deleteSubscriberByCurrentUserAndTargetUser(Long subscriberId, Long subscribedToId);

}
