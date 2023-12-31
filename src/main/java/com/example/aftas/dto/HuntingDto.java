package com.example.aftas.dto;

import jakarta.validation.constraints.Min;
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

    private Long id;

    @Builder.Default
    @Min(value = 1, message = "Number of fish must be at least 1")
    private int numberOfFish = 1;

    @NotBlank(message = "Competition code is required")
    @Pattern(regexp = "^[a-zA-Z]{3}-[0-9]{2}-[0-9]{2}-[0-9]{2}$", message = "Competition code must be in this format: (ex: loc-23-12-16)")
    private String competitionCode;

    @NotNull(message = "Member number is required")
    private Integer memberNum;

    @NotBlank(message = "Fish name is required")
    private String fishName;

    private MemberDto member;

    @NotNull(message = "Weight is required")
    @Min(value = 0, message = "Weight must be positive")
    private Double weight;
}
