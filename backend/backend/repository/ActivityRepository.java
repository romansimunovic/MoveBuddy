package com.movebuddy.backend.repository;

import com.movebuddy.backend.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    
    // Spring sam generira SQL upit na temelju naziva metode!
    List<Activity> findByUserId(Long userId);
}