package com.example.aftas.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ranking {
    @EmbeddedId
    private RankingKey id;

    @Builder.Default
    private Integer rank = 0;
    @Builder.Default
    private Integer score = 0;

    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    @JsonBackReference
    private Member member;

    @ManyToOne
    @JoinColumn(name = "competition_id", insertable = false, updatable = false)
    private Competition competition;

}
