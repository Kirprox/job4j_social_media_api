package ru.job4j.socialmediaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
            SELECT user FROM User as user
            where user.email = :email and user.password = :password""")
    Optional<User> findByEmailAndPassword(@Param("email") String login, @Param("password") String password);

    @Transactional
    @Modifying
    @Query("""
            update User u
            set u.userName = :#{#user.userName},
            u.email = :#{#user.email},
            u.password = :#{#user.password}
            where u.id=:#{#user.id}
            """)
    int update(@Param("user") User user);

    @Modifying
    @Query("delete from User u where u.id=:pId")
    int delete(@Param("pId") Long id);

    Optional<User> findByUserName(String userName);

    Boolean existsByEmail(String email);

    Boolean existsByUserName(String username);
}
