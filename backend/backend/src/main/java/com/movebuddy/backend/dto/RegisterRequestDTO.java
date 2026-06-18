package com.movebuddy.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "Ime korisnika je obavezno.")
    private String name;

    @NotBlank(message = "Email adresa je obavezna.")
    @Email(message = "Email adresa mora biti u ispravnom formatu.")
    private String email;

    @NotBlank(message = "Lozinka je obavezna.")
    @Size(min = 6, message = "Lozinka mora sadržavati minimalno 6 znakova.")
    private String password;
}