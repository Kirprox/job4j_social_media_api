package ru.job4j.socialmediaapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Friend Model Information")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user1_id")
    @Schema(description = "First user in the friendship")
    private User userA;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    @Schema(description = "Second user in the friendship")
    private User userB;
    @Schema(description = "Date of creation", example = "2023-10-15T15:15:15")
    private LocalDateTime createdAt;

    public Friend(User userA, User userB, LocalDateTime createdAt) {
        this.userA = userA;
        this.userB = userB;
        this.createdAt = createdAt;
    }
}
