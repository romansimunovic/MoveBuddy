package com.movebuddy.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotBlank(message = "Ime korisnika je obavezno.")
    private String name;

    @NotBlank(message = "Email adresa je obavezna.")
    @Email(message = "Email adresa mora biti u ispravnom formatu.")
    private String email;
}