package com.movebuddy.backend.service;

import com.movebuddy.backend.dto.RegisterRequestDTO;
import com.movebuddy.backend.dto.UpdateUserRequestDTO;
import com.movebuddy.backend.model.User;
import com.movebuddy.backend.model.Activity;
import com.movebuddy.backend.repository.UserRepository;
import com.movebuddy.backend.repository.ActivityRepository;
import com.movebuddy.backend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> getLeaderboard() {
        return userRepository.findAllByOrderByTotalPointsDesc();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Korisnik s ID-jem " + id + " ne postoji."));
    }

    @Transactional
    public User registerUser(RegisterRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Korisnik s ovom email adresom već postoji.");
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword())) // Lozinka se sprema kriptirana
                .totalPoints(0)
                .build();
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, UpdateUserRequestDTO dto) {
        User user = getUserById(id);
        user.setName(dto.getName());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        List<Activity> userActivities = activityRepository.findByUserId(id);
        activityRepository.deleteAll(userActivities);
        userRepository.delete(user);
    }
}