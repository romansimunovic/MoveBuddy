package com.movebuddy.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Data
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;

    private Double distance; // u kilometrima, npr. 5.4

    @Column(nullable = false)
    private Integer duration; // u minutama, npr. 45

    private Integer points; // bodovi zarađeni kretanjem (gejmifikacija)

    private LocalDateTime createdAt = LocalDateTime.now();

    // Spajanje s korisnikom preko user_id stranog ključa
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}