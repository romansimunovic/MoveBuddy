package com.movebuddy.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ActivityRequestDTO {
    @NotBlank(message = "Tip aktivnosti (npr. Trčanje, Biciklizam) je obavezan.")
    private String activityType;

    @NotNull(message = "Trajanje aktivnosti je obavezno.")
    @Min(value = 1, message = "Trajanje mora biti minimalno 1 minuta.")
    private Integer duration;

    @NotNull(message = "Udaljenost je obavezna.")
    @Positive(message = "Udaljenost mora biti veća od 0.")
    private Double distance;

    @NotNull(message = "ID korisnika je obavezan.")
    private Long userId;
}