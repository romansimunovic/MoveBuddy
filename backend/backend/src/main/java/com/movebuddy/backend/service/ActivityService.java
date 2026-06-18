package com.movebuddy.backend.service;

import com.movebuddy.backend.dto.ActivityRequestDTO;
import com.movebuddy.backend.model.Activity;
import com.movebuddy.backend.model.User;
import com.movebuddy.backend.repository.ActivityRepository;
import com.movebuddy.backend.repository.UserRepository;
import com.movebuddy.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Activity createActivity(ActivityRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Nemoguće dodati aktivnost. Korisnik s ID-jem " + dto.getUserId() + " ne postoji."));

        // Formula izračuna: 1 minuta = 10 bodova
        int computedPoints = dto.getDuration() * 10;

        Activity activity = Activity.builder()
                .activityType(dto.getActivityType())
                .duration(dto.getDuration())
                .distance(dto.getDistance())
                .points(computedPoints)
                .userId(dto.getUserId())
                .timestamp(LocalDateTime.now())
                .build();

        // Ažuriranje i spremanje korisnikovih bodova
        user.setTotalPoints(user.getTotalPoints() + computedPoints);
        userRepository.save(user);

        return activityRepository.save(activity);
    }

    @Transactional(readOnly = true)
    public List<Activity> getActivitiesByUser(Long userId) {
        return activityRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteActivity(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aktivnost s ID-jem " + id + " ne postoji."));

        User user = userRepository.findById(activity.getUserId()).orElse(null);
        if (user != null) {
            // Osiguravamo da bodovi nikad ne odu u minus prilikom brisanja aktivnosti
            user.setTotalPoints(Math.max(0, user.getTotalPoints() - activity.getPoints()));
            userRepository.save(user);
        }

        activityRepository.delete(activity);
    }
}