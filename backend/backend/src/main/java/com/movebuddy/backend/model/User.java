package com.movebuddy.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ime korisnika ne može biti prazno")
    private String name;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email mora biti u ispravnom formatu (npr. ime@domena.com)")
    @Column(nullable = false, unique = true)
    private String email;

    // Sustav bodovanja - kreće od 0
    private Integer totalPoints = 0;
}