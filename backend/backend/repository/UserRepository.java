package com.movebuddy.backend.repository;

import com.movebuddy.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository nam odmah daje metode poput save(), findAll(), findById()...
}