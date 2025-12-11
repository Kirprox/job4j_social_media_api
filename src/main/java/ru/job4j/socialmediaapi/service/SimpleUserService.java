package ru.job4j.socialmediaapi.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.socialmediaapi.model.User;
import ru.job4j.socialmediaapi.repository.UserRepository;
import ru.job4j.socialmediaapi.security.dtos.request.SignupRequestDTO;
import ru.job4j.socialmediaapi.security.dtos.response.RegisterDTO;
import ru.job4j.socialmediaapi.security.models.ERole;
import ru.job4j.socialmediaapi.security.models.Role;
import ru.job4j.socialmediaapi.security.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@AllArgsConstructor
@Service
public class SimpleUserService implements UserService {
    private final UserRepository repository;
    private PasswordEncoder encoder;
    private final RoleRepository roleRepository;

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

    public RegisterDTO signUp(SignupRequestDTO signUpRequest) {
        if (Boolean.TRUE.equals(repository.existsByUserName(signUpRequest.getUsername()))
                || Boolean.TRUE.equals(repository.existsByEmail(signUpRequest.getEmail()))) {
            return new RegisterDTO(HttpStatus.BAD_REQUEST, "Error: Username or Email is already taken!");
        }

        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        Supplier<RuntimeException> supplier = () -> new RuntimeException("Error: Role is not found.");

        if (strRoles == null) {
            roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(supplier));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> roles.add(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(supplier));
                    case "mod" -> roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(supplier));
                    default -> roles.add(roleRepository.findByName(ERole.ROLE_USER).orElseThrow(supplier));
                }
            });
        }
        user.setRoles(roles);
        repository.save(user);
        return new RegisterDTO(HttpStatus.OK, "Person registered successfully!");
    }
}
