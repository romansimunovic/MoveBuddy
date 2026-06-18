package com.movebuddy.backend.service;

import com.movebuddy.backend.model.User;
import com.movebuddy.backend.model.Activity;
import com.movebuddy.backend.repository.UserRepository;
import com.movebuddy.backend.repository.ActivityRepository;
import com.movebuddy.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getLeaderboard() {
        return userRepository.findAllByOrderByTotalPointsDesc();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Korisnik s ID-jem " + id + " ne postoji."));
    }

    public User createUser(User user) {
        if (user.getTotalPoints() == null) {
            user.setTotalPoints(0);
        }
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        
        // Sigurnosni korak: Prvo obriši sve aktivnosti ovog korisnika da ne ostanu siročad u bazi
        List<Activity> userActivities = activityRepository.findByUserId(id);
        activityRepository.deleteAll(userActivities);
        
        // Sada sigurno brišemo korisnika
        userRepository.delete(user);
    }
}