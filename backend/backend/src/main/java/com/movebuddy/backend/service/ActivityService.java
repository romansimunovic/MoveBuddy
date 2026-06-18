package com.movebuddy.backend.service;

import com.movebuddy.backend.dto.ActivityRequestDTO;
import com.movebuddy.backend.dto.UpdateActivityRequestDTO;
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
                .orElseThrow(() -> new ResourceNotFoundException("Korisnik s ID-jem " + dto.getUserId() + " ne postoji."));

        int computedPoints = dto.getDuration() * 10;

        Activity activity = Activity.builder()
                .activityType(dto.getActivityType())
                .duration(dto.getDuration())
                .distance(dto.getDistance())
                .points(computedPoints)
                .userId(dto.getUserId())
                .timestamp(LocalDateTime.now())
                .build();

        user.setTotalPoints(user.getTotalPoints() + computedPoints);
        userRepository.save(user);

        return activityRepository.save(activity);
    }

    @Transactional
    public Activity updateActivity(Long id, UpdateActivityRequestDTO dto) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aktivnost s ID-jem " + id + " ne postoji."));

        User user = userRepository.findById(activity.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Korisnik ove aktivnosti više ne postoji."));

        // Oduzimamo stare bodove ove aktivnosti iz ukupnog zbroja korisnika
        int oldPoints = activity.getPoints();
        int currentPointsWithoutActivity = user.getTotalPoints() - oldPoints;

        // Računamo nove bodove prema izmijenjenom trajanju
        int newComputedPoints = dto.getDuration() * 10;

        // Ažuriramo aktivnost
        activity.setActivityType(dto.getActivityType());
        activity.setDuration(dto.getDuration());
        activity.setDistance(dto.getDistance());
        activity.setPoints(newComputedPoints);

        // Dodajemo nove bodove korisniku (pazimo da ne ode ispod 0)
        user.setTotalPoints(Math.max(0, currentPointsWithoutActivity + newComputedPoints));
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
            user.setTotalPoints(Math.max(0, user.getTotalPoints() - activity.getPoints()));
            userRepository.save(user);
        }

        activityRepository.delete(activity);
    }
}