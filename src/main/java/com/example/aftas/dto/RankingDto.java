package com.example.aftas.dto;

import com.example.aftas.domain.Competition;
import com.example.aftas.domain.Member;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingDto {

    @Builder.Default
    @Min(value = 0, message = "Rank must be a positive number")
    private Integer rank = 0;

    @Builder.Default
    @Min(value = 0, message = "Score must be a positive number")
    private Integer score = 0;

    @NotNull(message = "Member id is required")
    @Min(value = 1, message = "Member id must be a positive number")
    private Member member;

    @NotNull(message = "Competition id is required")
    @Min(value = 1, message = "Competition id must be a positive number")
    private Competition competition;

}
