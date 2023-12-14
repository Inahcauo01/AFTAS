package com.example.aftas.web.rest;

import com.example.aftas.dto.LevelDto;
import com.example.aftas.mapper.LevelDtoMapper;
import com.example.aftas.seeder.LevelSeeder;
import com.example.aftas.service.LevelService;
import com.example.aftas.utils.CustomError;
import com.example.aftas.utils.Response;
import com.example.aftas.utils.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/level")
public class LevelController {
    private final LevelService levelService;
    private final LevelSeeder levelSeeder;

    @GetMapping
    public ResponseEntity<Response<List<LevelDto>>> findAll() {
        Response<List<LevelDto>> response = new Response<>();
        List<LevelDto> levelList = levelService.findAll().stream()
                .map(LevelDtoMapper::toDto)
                .toList();
        response.setResult(levelList);
        if (levelList.isEmpty()) {
            response.setMessage("There is no level in the database");
        } else {
            response.setMessage("Level list retrieved successfully");
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<LevelDto>> save(@Valid @RequestBody LevelDto levelDto) throws ValidationException {
        Response<LevelDto> response = new Response<>();
        response.setResult(LevelDtoMapper.toDto(levelService.save(LevelDtoMapper.toEntity(levelDto))));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<LevelDto>> findById(Long id) throws ValidationException {
        Response<LevelDto> response = new Response<>();
        response.setResult(LevelDtoMapper.toDto(levelService.findById(id)
                .orElseThrow(() -> new ValidationException(new CustomError("id", "Level not found")))));
        if (response.getResult() == null) {
            response.setMessage("Level not found");
        } else {
            response.setMessage("Level retrieved successfully");
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/seed")
    public ResponseEntity<Response<List<LevelDto>>> seed() throws ValidationException {
        levelSeeder.seedLevels();
        Response<List<LevelDto>> response = new Response<>();
        response.setResult(levelService.findAll().stream()
                .map(LevelDtoMapper::toDto)
                .toList());
        response.setMessage("Levels seeded successfully");
        return ResponseEntity.ok().body(response);
    }
}
