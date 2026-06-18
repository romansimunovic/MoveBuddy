package com.movebuddy.backend.controller;

import com.movebuddy.backend.model.User;
import com.movebuddy.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Ruta za dohvaćanje svih korisnika: GET /api/users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Ruta za kreiranje novog korisnika: POST /api/users
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}