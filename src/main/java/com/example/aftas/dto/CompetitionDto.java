package com.example.aftas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetitionDto {
    private String code;

    @NotNull(message = "Date must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull(message = "Start time must not be null")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    @Builder.Default
    private LocalTime endTime = LocalTime.parse("23:59");

    @Min(value = 5, message = "Number of participants must be at least 5")
    @NotNull(message = "Number of participants must not be null")
    private Integer numberOfParticipants;

    @NotNull(message = "Location must not be null")
    @Size(min = 3, max = 50, message = "Location must be between 3 and 50 characters")
    private String location;

    @NotNull(message = "Amount must not be null")
    @Min(value = 0, message = "Amount must be a positive number")
    private Double amount;

}
