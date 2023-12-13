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

//    @GetMapping("/seed")
//    public void seed() throws ValidationException {
//        fishSeeder.seedFish();
//    }

    @GetMapping("/seed")
    public ResponseEntity<Response<List<FishDto>>> seed() throws ValidationException {
        fishSeeder.seedFish();

        // Assuming you want to return the list of fish after seeding
        List<FishDto> seededFishList = fishService.findAll()
                .stream()
                .map(FishDtoMapper::toDto)
                .collect(Collectors.toList());

        Response<List<FishDto>> response = new Response<>();
        response.setResult(seededFishList);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping
    public ResponseEntity<Response<List<FishDto>>> getAllFish() {
        Response<List<FishDto>> response = new Response<>();
        List<FishDto> fishList = fishService.findAll()
                .stream()
                .map(FishDtoMapper::toDto)
                .collect(Collectors.toList());
        response.setResult(fishList);
        return ResponseEntity.ok().body(response);
    }


}
