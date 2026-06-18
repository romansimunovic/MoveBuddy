package com.movebuddy.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoClassDefFoundError;

@Entity
@Table(name = "users") // 'user' je rezervirana riječ u Postgresu, pa je 'users' sigurnije
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    // Ovdje kasnije lako dodamo lozinku, uloge (ROLES), bodove itd.
}