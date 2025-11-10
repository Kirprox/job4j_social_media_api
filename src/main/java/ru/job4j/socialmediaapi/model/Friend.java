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
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User userA;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User userB;
    private LocalDateTime createdAt;

    public Friend(User userA, User userB, LocalDateTime createdAt) {
        this.userA = userA;
        this.userB = userB;
        this.createdAt = createdAt;
    }
}
