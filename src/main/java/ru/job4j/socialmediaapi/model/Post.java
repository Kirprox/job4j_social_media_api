package ru.job4j.socialmediaapi.model;

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
    @JoinColumn(name = "user_id") // foreign key
    private User user;
    @ManyToOne
    @JoinColumn(name = "file_id")
    private Files file;
}
