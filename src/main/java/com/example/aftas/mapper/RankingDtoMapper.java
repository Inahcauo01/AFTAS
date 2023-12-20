package com.example.aftas.mapper;


import com.example.aftas.domain.Ranking;
import com.example.aftas.dto.RankingDto;

public class RankingDtoMapper {
    public static RankingDto toDto(Ranking ranking){
        return RankingDto.builder()
                .member(ranking.getMember())
                .competition(ranking.getCompetition())
                .score(ranking.getScore())
                .rank(ranking.getRank())
                .build();
    }

    public static Ranking toEntity(RankingDto rankingDto){
        return Ranking.builder()
                .rank(rankingDto.getRank())
                .score(rankingDto.getScore())
                .member(rankingDto.getMember())
                .competition(rankingDto.getCompetition())
                .build();
    }
}
