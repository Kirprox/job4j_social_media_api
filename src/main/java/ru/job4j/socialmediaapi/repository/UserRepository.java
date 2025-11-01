package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmediaapi.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("""
            SELECT user FROM User as user
            where user.email = :email and user.password = :password""")
    Optional<User> findByEmailAndPassword(@Param("email")String login, @Param("password") String password);
}
