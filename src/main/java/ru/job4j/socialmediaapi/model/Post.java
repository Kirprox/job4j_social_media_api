package ru.job4j.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "title не может быть пустым")
    @Length(min = 4,
            max = 15,
            message = "title должен быть не менее 4 и не более 15 символов")
    private String title;
    @NotBlank(message = "text не может быть пустым")
    @Length(min = 2,
            max = 500,
            message = "text должен быть не менее 2 и не более 500 символов")
    private String text;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "file_id")
    private File file;

    public Post(String title, String text, LocalDateTime created, User user, File file) {
        this.title = title;
        this.text = text;
        this.created = created;
        this.user = user;
        this.file = file;
    }
}
