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
            
            // Izvlačenje našeg prilagođenog User objekta iz autentifikacije
            User user = (User) authentication.getPrincipal();

            return ResponseEntity.ok(new AuthResponseDTO(jwt, "Bearer", user.getId(), user.getName()));
        }
        public class HealthCheckController {

    // Kada Render pogodi čisti URL (https://movebuddy-backend.onrender.com/),
    // Spring Boot će odmah vratiti poruku da je živ i Render ga NEĆE ugasiti!
    @GetMapping("/")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("MoveBuddy Backend is Live and Running! 🚀");
    }
}
    }