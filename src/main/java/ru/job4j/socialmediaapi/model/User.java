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

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "social_user")
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
    private String fullName;
    @NotBlank(message = "email не может быть пустым")
    @Email
    private String email;
    @NotBlank(message = "password не может быть пустым")
    @Length(min = 15,
            max = 30,
            message = "password должен быть не менее 15 и не более 30 символов")
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscriber> subscriptions = new ArrayList<>();
    @OneToMany(mappedBy = "subscribedTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscriber> subscribers = new ArrayList<>();
}
