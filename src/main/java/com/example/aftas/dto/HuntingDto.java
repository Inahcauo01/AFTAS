package com.example.aftas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HuntingDto {

    @Builder.Default
    private int numberOfFish = 1;

    @NotBlank(message = "Competition code is required")
    @Pattern(regexp = "^[a-zA-Z]{3}-[0-9]{2}-[0-9]{2}-[0-9]{2}$", message = "Competition code must be in this format: (ex: loc-23-12-16)")
    private String competitionCode;

    @NotNull(message = "Member number is required")
    private Integer memberNum;

    @NotBlank(message = "Fish name is required")
    private String fishName;
}
