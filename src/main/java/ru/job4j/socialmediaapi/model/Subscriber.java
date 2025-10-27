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
@Table(name = "subscribers")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private User subscriber;
    @ManyToOne
    @JoinColumn(name = "subscriber_to_id")
    private User subscribedTo;
    private LocalDateTime createdAt;
}
