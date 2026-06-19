package com.movebuddy.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
// import com.example.movebuddy.entity.User; // <-- Odkomentiraj i prilagodi ako ti je User entitet u drugom paketu

@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender; // Korisnik koji šalje poziv (Ti)

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver; // Korisnik koji prima poziv (npr. Ivan Horvat)

    @Column(nullable = false)
    private String status; // Vrijednosti: "PENDING", "ACCEPTED", "REJECTED"

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // 1. Prazan konstruktor (Apsolutno obavezan za Hibernate/JPA)
    public Invitation() {
    }

    // 2. Konstruktor s parametrima za lakše kreiranje poziva u kontroleru
    public Invitation(User sender, User receiver, String status, LocalDateTime timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.timestamp = timestamp;
    }

    // ==========================================
    //            GETTERI I SETTERI
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}