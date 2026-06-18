package com.movebuddy.backend.controller;

import com.movebuddy.backend.model.Activity;
import com.movebuddy.backend.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<Activity> createActivity(@Valid @RequestBody Activity activity) {
        Activity created = activityService.createActivity(activity);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public List<Activity> getActivitiesByUser(@PathVariable Long userId) {
        return activityService.getActivitiesByUser(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        Map<String, String> response = new java.util.HashMap<>();
        response.put("message", "Aktivnost uspješno obrisana, a bodovi korisnika su ažurirani.");
        return ResponseEntity.ok(response);
    }
}