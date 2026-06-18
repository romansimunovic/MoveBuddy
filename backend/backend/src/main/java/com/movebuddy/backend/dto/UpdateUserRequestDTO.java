package com.movebuddy.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequestDTO {
    @NotBlank(message = "Ime korisnika ne može biti prazno.")
    private String name;
}