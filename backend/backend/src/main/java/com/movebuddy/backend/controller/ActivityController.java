package com.movebuddy.backend.controller;

import com.movebuddy.backend.dto.ActivityRequestDTO;
import com.movebuddy.backend.model.Activity;
import com.movebuddy.backend.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<Activity> createActivity(@Valid @RequestBody ActivityRequestDTO dto) {
        Activity created = activityService.createActivity(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public List<Activity> getActivitiesByUser(@PathVariable Long userId) {
        return activityService.getActivitiesByUser(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Aktivnost je uklonjena, a bodovni saldo korisnika ažuriran.");
        return ResponseEntity.ok(response);
    }
}