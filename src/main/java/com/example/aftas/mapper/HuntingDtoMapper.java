package com.example.aftas.mapper;

import com.example.aftas.domain.Competition;
import com.example.aftas.domain.Fish;
import com.example.aftas.domain.Hunting;
import com.example.aftas.domain.Member;
import com.example.aftas.dto.HuntingDto;

public class HuntingDtoMapper {

    public static HuntingDto toDto(Hunting hunting) {
        return HuntingDto.builder()
                .id(hunting.getId())
                .numberOfFish(hunting.getNumberOfFish())
                .competitionCode(hunting.getCompetition().getCode())
                .memberNum(hunting.getMember().getNum())
                .fishName(hunting.getFish().getName())
                .member(MemberDtoMapper.toDto(hunting.getMember()))
                .weight(hunting.getWeight())
                .build();
    }

    public static Hunting toEntity(HuntingDto huntingDto) {
        return Hunting.builder()
                .numberOfFish(huntingDto.getNumberOfFish())
                .member(Member.builder().num(huntingDto.getMemberNum()).build())
                .competition(Competition.builder().code(huntingDto.getCompetitionCode()).build())
                .fish(Fish.builder().name(huntingDto.getFishName()).build())
                .weight(huntingDto.getWeight())
                .build();
    }
}
