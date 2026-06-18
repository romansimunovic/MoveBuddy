package com.movebuddy.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activityType; // npr. "Trčanje", "Hodanje", "Biciklizam"
    private Integer duration;    // Trajanje u minutama
    private Double distance;     // Udaljenost u kilometrima
    private Integer points;      // Izračunati bodovi
    private Long userId;         // Poveznica na korisnika

    private LocalDateTime timestamp = LocalDateTime.now();
}