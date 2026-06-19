package com.movebuddy.backend.controller;

import com.movebuddy.backend.dto.AuthResponseDTO;
import com.movebuddy.backend.dto.LoginRequestDTO;
import com.movebuddy.backend.dto.RegisterRequestDTO;
import com.movebuddy.backend.model.User;
import com.movebuddy.backend.security.JwtTokenProvider;
import com.movebuddy.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // ✅ Health check je sada dostupan na: https://movebuddy-backend.onrender.com/api/auth/
    @GetMapping("/")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("MoveBuddy Backend is Live and Running! 🚀");
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterRequestDTO dto) {
        User registeredUser = userService.registerUser(dto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication.getName());
        
        // ✅ Popravljeno: Sigurno i stabilno izvlačenje iz baze podataka
        User user = userService.findByEmail(dto.getEmail());

        return ResponseEntity.ok(new AuthResponseDTO(jwt, "Bearer", user.getId(), user.getName()));
    }
}