package com.example.aftas.web.rest;

import com.example.aftas.dto.FishDto;
import com.example.aftas.mapper.FishDtoMapper;
import com.example.aftas.seeder.FishSeeder;
import com.example.aftas.service.FishService;
import com.example.aftas.utils.Response;
import com.example.aftas.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fish")
public class FishController {
    private final FishService fishService;
    private final FishSeeder fishSeeder;

    @GetMapping("/seed")
    public ResponseEntity<Response<List<FishDto>>> seed() throws ValidationException {
        fishSeeder.seedFish();

        List<FishDto> seededFishList = fishService.findAll()
                .stream()
                .map(FishDtoMapper::toDto)
                .toList();

        Response<List<FishDto>> response = new Response<>();
        response.setResult(seededFishList);
        response.setMessage("Fish seeded successfully");
        return ResponseEntity.ok().body(response);
    }


    @GetMapping
    public ResponseEntity<Response<List<FishDto>>> getAllFish() {
        Response<List<FishDto>> response = new Response<>();
        List<FishDto> fishList = fishService.findAll()
                .stream()
                .map(FishDtoMapper::toDto)
                .toList();
        response.setResult(fishList);
        if (fishList.isEmpty()) {
            response.setMessage("There is no fish in the database");
        } else {
            response.setMessage("Fish list retrieved successfully");
        }
        return ResponseEntity.ok().body(response);
    }


}
