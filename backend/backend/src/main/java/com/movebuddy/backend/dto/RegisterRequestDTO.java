package com.movebuddy.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {

    @NotBlank(message = "Ime ne smije biti prazno")
    private String name;

    @NotBlank(message = "Email ne smije biti prazno")
    @Email(message = "Email mora biti u ispravnom formatu")
    private String email;

    @NotBlank(message = "Lozinka ne smije biti prazna")
    @Size(min = 6, message = "Lozinka mora imati barem 6 znakova")
    private String password;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}