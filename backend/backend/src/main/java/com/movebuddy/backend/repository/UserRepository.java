package com.movebuddy.backend.repository;

import com.movebuddy.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByTotalPointsDesc();
    Optional<User> findByEmail(String email); // Dodano za potrebe autentifikacije
}