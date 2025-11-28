package ru.job4j.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Post Model Information")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "title не может быть пустым")
    @Length(min = 4,
            max = 15,
            message = "title должен быть не менее 4 и не более 15 символов")
    @Schema(description = "Title of the post", example = "My first post")
    private String title;
    @NotBlank(message = "text не может быть пустым")
    @Length(min = 2,
            max = 500,
            message = "text должен быть не менее 2 и не более 500 символов")
    @Schema(description = "Text content of the post",
            example = "This is a sample post content")
    private String text;
    @Schema(description = "Date of creation", example = "2023-10-15T15:15:15")
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "file_id")
    @Schema(description = "Author of the post")
    private File file;

    public Post(String title, String text, LocalDateTime created, User user, File file) {
        this.title = title;
        this.text = text;
        this.created = created;
        this.user = user;
        this.file = file;
    }
}
