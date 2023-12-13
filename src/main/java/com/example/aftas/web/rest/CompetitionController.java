package com.example.aftas.web.rest;

import com.example.aftas.domain.Competition;
import com.example.aftas.domain.Ranking;
import com.example.aftas.dto.CompetitionDto;
import com.example.aftas.dto.RankingDto;
import com.example.aftas.mapper.CompetitionDtoMapper;
import com.example.aftas.mapper.RankingDtoMapper;
import com.example.aftas.service.CompetitionService;
import com.example.aftas.utils.Response;
import com.example.aftas.utils.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/competition")
@RequiredArgsConstructor
public class CompetitionController {
    private final CompetitionService competitionService;

    @GetMapping
    public ResponseEntity<Response<List<CompetitionDto>>> getAllMembers(){
        Response<List<CompetitionDto>> response = new Response<>();
        List<CompetitionDto> competitionList = new ArrayList<>();
        competitionService.getAllCompetitions().stream().map(CompetitionDtoMapper::toDto).forEach(competitionList::add);
        response.setResult(competitionList);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<CompetitionDto>> create(@Valid @RequestBody CompetitionDto competitionDto){
        Response<CompetitionDto> response = new Response<>();
        Competition competition = CompetitionDtoMapper.toEntity(competitionDto);
        try {
            response.setResult(CompetitionDtoMapper.toDto(competitionService.save(competition)));
            response.setMessage("Competition has been added successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ValidationException e) {
            response.setMessage("Competition has not been added");
            response.setErrors(List.of(e.getCustomError()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

@PostMapping("/{competitionId}/members/{memberId}")
public ResponseEntity<Response<RankingDto>> addMemberToCompetition(@PathVariable Long competitionId, @PathVariable Long memberId) {
    Response<RankingDto> response = new Response<>();
    try {
        Ranking createdRanking = competitionService.addMemberToCompetition(memberId, competitionId);
        RankingDto createdRankingDto = RankingDtoMapper.toDto(createdRanking);

        response.setResult(createdRankingDto);
        response.setMessage("Member has been added to the competition successfully");
        return ResponseEntity.ok(response);
    } catch (ValidationException e) {
        response.setMessage("Member has not been added to the competition");
        response.setErrors(List.of(e.getCustomError()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}


}
