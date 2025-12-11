package ru.job4j.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.job4j.socialmediaapi.security.models.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "social_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Schema(description = "User Model Information")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "fullName не может быть пустым")
    @Length(min = 2,
            max = 15,
            message = "fullName должно быть не менее 2 и не более 15 символов")
    @Schema(description = "UserName title", example = "Mediator")
    @Column(name = "username")
    private String userName;
    @NotBlank(message = "email не может быть пустым")
    @Email
    private String email;
    @NotBlank(message = "password не может быть пустым")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)

    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscriber> subscriptions = new ArrayList<>();
    @OneToMany(mappedBy = "subscribedTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscriber> subscribers = new ArrayList<>();

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String userName, String email, String password, List<Post> posts, List<Subscriber> subscriptions, List<Subscriber> subscribers) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.posts = posts;
        this.subscriptions = subscriptions;
        this.subscribers = subscribers;
    }
}
