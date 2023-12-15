package com.example.aftas.web.rest;

import com.example.aftas.domain.Ranking;
import com.example.aftas.service.RankingService;
import com.example.aftas.utils.Response;
import com.example.aftas.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("{CompetitionId}")
    public ResponseEntity<Response<List<Ranking>>> getRankingByCompetitionCode(@PathVariable Long CompetitionId) throws ValidationException {
        Response<List<Ranking>> response = new Response<>();
        response.setResult(rankingService.getRankingByCompetitionId(CompetitionId));
        if (response.getResult().isEmpty())
            response.setMessage("No ranking found for this competition");
        return ResponseEntity.ok().body(response);
    }

}
