package com.movebuddy.backend.repository;

import com.movebuddy.backend.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    // Ovdje Spring Data rješava sve osnovne operacije (save, findById, delete...)
}