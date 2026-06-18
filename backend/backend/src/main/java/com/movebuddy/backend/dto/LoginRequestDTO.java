package com.movebuddy.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email je obavezan.")
    @Email(message = "Neispravan format email adrese.")
    private String email;

    @NotBlank(message = "Lozinka je obavezna.")
    private String password;
}