package com.example.aftas.web.rest;

import com.example.aftas.domain.Hunting;
import com.example.aftas.domain.Member;
import com.example.aftas.dto.HuntingDto;
import com.example.aftas.dto.MemberDto;
import com.example.aftas.mapper.HuntingDtoMapper;
import com.example.aftas.mapper.MemberDtoMapper;
import com.example.aftas.service.HuntingService;
import com.example.aftas.utils.CustomError;
import com.example.aftas.utils.Response;
import com.example.aftas.utils.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hunting")
@RequiredArgsConstructor
public class HuntingController {
    private final HuntingService huntingService;

    @GetMapping
    public ResponseEntity<Response<List<HuntingDto>>> getAllHuntings() {
        Response<List<HuntingDto>> response = new Response<>();
        List<HuntingDto> huntingDtos = huntingService.getAllHuntings()
                .stream()
                .map(HuntingDtoMapper::toDto)
                .toList();
        response.setResult(huntingDtos);
        if (huntingDtos.isEmpty()) {
            response.setMessage("There is no hunting in the database");
        } else {
            response.setMessage("Hunting list retrieved successfully");
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<HuntingDto>> saveHunting(@Valid @RequestBody HuntingDto huntingDto) throws ValidationException {
        Response<HuntingDto> response = new Response<>();
        Hunting h = HuntingDtoMapper.toEntity(huntingDto);
        Hunting hunting = huntingService.save(h);
        response.setResult(HuntingDtoMapper.toDto(hunting));
        response.setMessage("Hunting saved successfully");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<HuntingDto>> getHuntingById(Long id) throws ValidationException {
        Response<HuntingDto> response = new Response<>();
        Hunting hunting = huntingService.getHuntingById(id)
                .orElseThrow(() -> new ValidationException(new CustomError("hunting", "hunting not found")));
        response.setResult(HuntingDtoMapper.toDto(hunting));
        response.setMessage("Hunting retrieved successfully");
        return ResponseEntity.ok().body(response);
    }
}
