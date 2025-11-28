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
@Table(name = "subscribers")
@Schema(description = "Subscriber Model Information")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    @Schema(description = "User who subscribers")
    private User subscriber;
    @ManyToOne
    @JoinColumn(name = "subscriber_to_id")
    @Schema(description = "User who is being subscribed to")
    private User subscribedTo;
    @Schema(description = "Date of creation", example = "2023-10-15T15:15:15")
    private LocalDateTime createdAt;

    public Subscriber(User subscriber, User subscribedTo, LocalDateTime createdAt) {
        this.subscriber = subscriber;
        this.subscribedTo = subscribedTo;
        this.createdAt = createdAt;
    }
}
