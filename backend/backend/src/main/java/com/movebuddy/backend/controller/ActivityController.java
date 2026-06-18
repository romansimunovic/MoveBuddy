package com.movebuddy.backend.controller;

import com.movebuddy.backend.model.Activity;
import com.movebuddy.backend.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    // 1. Ruta za spremanje nove aktivnosti: POST /api/activities
    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        // Jednostavna logika za bodove: 1 minuta trčanja/hodanja = 10 bodova
        if (activity.getPoints() == null) {
            activity.setPoints(activity.getDuration() * 10);
        }
        return activityRepository.save(activity);
    }

    // 2. Ruta za dohvaćanje svih aktivnosti određenog korisnika: GET /api/activities/user/{userId}
    @GetMapping("/user/{userId}")
    public List<Activity> getActivitiesByUser(@PathVariable Long userId) {
        return activityRepository.findByUserId(userId);
    }
}