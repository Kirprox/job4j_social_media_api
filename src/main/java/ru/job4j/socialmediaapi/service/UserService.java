package ru.job4j.socialmediaapi.service;

import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.security.dtos.request.SignupRequestDTO;
import ru.job4j.socialmediaapi.security.dtos.response.RegisterDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    boolean update(User user);

    boolean deleteById(Long id);

    Optional<User> findById(Long id);

    List<User> findAll();

    RegisterDTO signUp(SignupRequestDTO signUpRequest);
}
