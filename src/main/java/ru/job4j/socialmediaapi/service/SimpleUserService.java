package ru.job4j.socialmediaapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleUserService implements UserService {
    private final UserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public boolean update(User user) {
        return repository.update(user) > 0L;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean result = repository.existsById(id);
        if (result) {
            repository.deleteById(id);
        }
        return result;
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
}
