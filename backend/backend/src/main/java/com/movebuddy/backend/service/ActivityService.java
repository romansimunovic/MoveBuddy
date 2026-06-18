package com.movebuddy.backend.service;

import com.movebuddy.backend.model.Activity;
import com.movebuddy.backend.model.User;
import com.movebuddy.backend.repository.ActivityRepository;
import com.movebuddy.backend.repository.UserRepository;
import com.movebuddy.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Activity createActivity(Activity activity) {
        // Provjeri postoji li korisnik uopće u sustavu
        User user = userRepository.findById(activity.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Nemoguće dodati aktivnost. Korisnik s ID-jem " + activity.getUserId() + " ne postoji."));

        // Izračunaj bodove ako nisu poslani (1 minuta = 10 bodova)
        if (activity.getPoints() == null) {
            activity.setPoints(activity.getDuration() * 10);
        }

        // Automatsko ažuriranje ukupnih bodova korisnika
        user.setTotalPoints(user.getTotalPoints() + activity.getPoints());
        userRepository.save(user);

        return activityRepository.save(activity);
    }

    public List<Activity> getActivitiesByUser(Long userId) {
        return activityRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteActivity(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aktivnost s ID-jem " + id + " ne postoji."));

        User user = userRepository.findById(activity.getUserId()).orElse(null);
        if (user != null) {
            // Oduzmi bodove korisniku za obrisanu aktivnost (pazi da ne ode ispod 0)
            user.setTotalPoints(Math.max(0, user.getTotalPoints() - activity.getPoints()));
            userRepository.save(user);
        }

        activityRepository.delete(activity);
    }
}