package com.example.aftas.service.impl;

import com.example.aftas.domain.Competition;
import com.example.aftas.domain.Member;
import com.example.aftas.domain.Ranking;
import com.example.aftas.repository.RankingRepository;
import com.example.aftas.service.RankingService;
import com.example.aftas.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {
    private final RankingRepository rankingRepository;

    @Override
    public List<Ranking> getAllRankings() {
        return rankingRepository.findAll();
    }

    @Override
    public Ranking save(Ranking ranking) throws ValidationException {
        return rankingRepository.save(ranking);
    }

    @Override
    public Ranking update(Ranking ranking) throws ValidationException {
        return rankingRepository.save(ranking);
    }

    @Override
    public void deleteRankingById(Long id) throws ValidationException {

    }

    @Override
    public Ranking getRankingById(Long id) throws ValidationException {
        return null;
    }

    @Override
    public List<Ranking> getRankingByCompetitionCode(String code) throws ValidationException {
        return rankingRepository.findByCompetition_Code(code);
    }

    @Override
    public List<Ranking> getRankingByMemberAndCompetition(Member member, Competition competition) throws ValidationException {
        return rankingRepository.findByMemberAndCompetition(Member.builder().build(), Competition.builder().build());
    }

}
