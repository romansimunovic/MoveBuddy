package com.movebuddy.backend.repository;

import com.movebuddy.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Automatski generira upit za rang ljestvicu
    List<User> findAllByOrderByTotalPointsDesc();
}