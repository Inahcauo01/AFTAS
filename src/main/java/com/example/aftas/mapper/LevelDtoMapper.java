package com.example.aftas.mapper;

import com.example.aftas.domain.Level;
import com.example.aftas.dto.LevelDto;

public class LevelDtoMapper {

    public static LevelDto toDto(Level level) {
        return LevelDto.builder()
                .code(level.getCode())
                .description(level.getDescription())
                .points(level.getPoints())
                .build();
    }

    public static Level toEntity(LevelDto levelDto) {
        return Level.builder()
                .code(levelDto.getCode())
                .description(levelDto.getDescription())
                .points(levelDto.getPoints())
                .build();
    }
}
