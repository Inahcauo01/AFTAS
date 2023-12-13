package com.example.aftas.mapper;

import com.example.aftas.domain.Competition;
import com.example.aftas.domain.Member;
import com.example.aftas.domain.Ranking;
import com.example.aftas.dto.RankingDto;

public class RankingDtoMapper {
    public static RankingDto toDto(Ranking ranking){
        return RankingDto.builder()
                .id(ranking.getId())
                .memberId(ranking.getMember().getId())
                .competitionId(ranking.getCompetition().getId())
                .rank(ranking.getRank())
                .build();
    }

    public static Ranking toEntity(RankingDto rankingDto){
        return Ranking.builder()
                .id(rankingDto.getId())
                .rank(rankingDto.getRank())
                .member(
                        Member.builder()
                                .id(rankingDto.getMemberId())
                                .build()
                )
                .competition(
                        Competition.builder()
                                .id(rankingDto.getCompetitionId())
                                .build()
                )
                .build();
    }
}
