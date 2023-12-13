package com.example.aftas.mapper;

import com.example.aftas.domain.Fish;
import com.example.aftas.dto.FishDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class FishDtoMapper {
    public static FishDto toDto(Fish fish) {
        return FishDto.builder()
                .id(fish.getId())
                .name(fish.getName())
                .averageWeight(fish.getAverageWeight())
                .build();
    }

    public static Fish toEntity(FishDto fishDto) {
        return Fish.builder()
                .id(fishDto.getId())
                .name(fishDto.getName())
                .averageWeight(fishDto.getAverageWeight())
                .build();
    }
}
