package com.example.aftas.seeder;

import com.example.aftas.domain.Fish;
import com.example.aftas.domain.Level;
import com.example.aftas.service.FishService;
import com.example.aftas.service.LevelService;
import com.example.aftas.utils.CustomError;
import com.example.aftas.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FishSeeder {
    private final FishService fishService;
    private final LevelService levelService;
    public void seedFish() throws ValidationException {
        List<Level> levels = levelService.findAll();
        if (levels.isEmpty()) {
            throw new ValidationException(new CustomError("levels", "Levels should be seeded first"));
        }

        List<Fish> fishList = Arrays.asList(
                Fish.builder().name("Saumon").averageWeight(5.0).level(levels.get(0)).build(),
                Fish.builder().name("Truite").averageWeight(2.5).level(levels.get(1)).build(),
                Fish.builder().name("Bar").averageWeight(3.8).level(levels.get(2)).build(),
                Fish.builder().name("Silure").averageWeight(1.2).level(levels.get(3)).build(),
                Fish.builder().name("Thon").averageWeight(4.5).level(levels.get(4)).build(),
                Fish.builder().name("Sardine").averageWeight(0.5).level(levels.get(0)).build(),
                Fish.builder().name("Maquereau").averageWeight(1.8).level(levels.get(1)).build(),
                Fish.builder().name("Hareng").averageWeight(0.8).level(levels.get(2)).build(),
                Fish.builder().name("Anchois").averageWeight(0.3).level(levels.get(3)).build(),
                Fish.builder().name("Morue").averageWeight(2.0).level(levels.get(4)).build(),
                Fish.builder().name("Églefin").averageWeight(1.5).level(levels.get(0)).build(),
                Fish.builder().name("Lieue").averageWeight(1.0).level(levels.get(1)).build()
        );

        fishService.saveAll(fishList);
    }
}
