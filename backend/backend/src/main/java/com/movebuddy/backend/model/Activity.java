package com.movebuddy.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotBlank(message = "Tip aktivnosti (npr. Trčanje, Biciklizam) je obavezan")
    private String activityType;

    @NotNull(message = "Trajanje aktivnosti je obavezno")
    @Min(value = 1, message = "Trajanje aktivnosti mora biti minimalno 1 minuta")
    private Integer duration;

    @NotNull(message = "Udaljenost je obavezna")
    @Positive(message = "Udaljenost mora biti veća od 0")
    private Double distance;

    private Integer points; 

    @NotNull(message = "ID korisnika (userId) je obavezan")
    private Long userId;

    private LocalDateTime timestamp = LocalDateTime.now();
}