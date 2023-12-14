package com.example.aftas.seeder;

import com.example.aftas.domain.Fish;
import com.example.aftas.domain.Level;
import com.example.aftas.service.LevelService;
import com.example.aftas.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LevelSeeder {
    private final LevelService levelService;

    public void seedLevels() throws ValidationException {
        List<Level> levelList = Arrays.asList(
                Level.builder().description("Débutant").points(0).build(),
                Level.builder().description("Amateur").points(100).build(),
                Level.builder().description("Confirmé").points(200).build(),
                Level.builder().description("Expert").points(300).build(),
                Level.builder().description("Maître").points(400).build(),
                Level.builder().description("Légende").points(500).build()
        );

        levelService.saveAll(levelList);
    }
}
