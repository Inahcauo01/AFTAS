package com.example.aftas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Num is required")
    @Min(value = 1, message = "Num must be a positive number")
    private Integer num;

    @NotNull(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Family name is required")
    @Size(min = 2, max = 50, message = "Family name must be between 2 and 50 characters")
    private String familyName;

    @NotNull(message = "Accession date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate accessionDate;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @NotNull(message = "Identity document is required")
    private String identityDocument;

    @NotBlank(message = "Identity number is required")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "Identity number must have letters and numbers and length must be between 5 and 20 characters")
    private String identityNumber;
}
