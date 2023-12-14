package com.example.aftas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FishDto {
    private Long id;

    @NotBlank(message = "Fish name is required")
    @Size(min = 2, max = 50, message = "Fish name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Average weight is required")
    @Min(value = 0, message = "Average weight must be a positive number")
    private Double averageWeight;

    @NotNull(message = "Level code is required")
    @Min(value = 1, message = "Level code must be a positive number")
    private Long levelCode;
}
