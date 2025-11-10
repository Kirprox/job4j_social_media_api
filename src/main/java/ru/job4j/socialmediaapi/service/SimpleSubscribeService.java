package ru.job4j.socialmediaapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmediaapi.model.Subscriber;
import ru.job4j.socialmediaapi.repository.SubscriberRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleSubscribeService implements SubscribeService {
    private final SubscriberRepository subscriberRepository;

    @Override
    public Subscriber save(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    @Override
    public Optional<Subscriber> findBySubscriberAndSubscribedTo(Long subscriberId, Long subscribedToId) {
        return subscriberRepository.findBySubscriberAndSubscribedTo(subscriberId, subscribedToId);
    }

    @Override
    public List<Subscriber> findAllSubscribersByUserId(Long id) {
        return subscriberRepository.findAllSubscribersByUserId(id);
    }

    @Override
    public void deleteSubscriberByCurrentUserAndTargetUser(Long subscriberId, Long subscribedToId) {
        subscriberRepository.deleteBySubscriberAndSubscribedToId(subscriberId, subscribedToId);
    }
}
