package ru.job4j.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String title;
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
