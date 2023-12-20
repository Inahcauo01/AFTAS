package com.example.aftas.service.impl;

import com.example.aftas.domain.Competition;
import com.example.aftas.domain.Member;
import com.example.aftas.domain.Ranking;
import com.example.aftas.repository.RankingRepository;
import com.example.aftas.service.RankingService;
import com.example.aftas.utils.CustomError;
import com.example.aftas.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        if (rankingRepository.existsById(id))
            rankingRepository.deleteById(id);
        else
            throw new ValidationException(new CustomError("id", "Ranking with id " + id + " not found"));
    }

    @Override
    public Ranking getRankingById(Long id) throws ValidationException {
        return rankingRepository.findById(id)
                .orElseThrow(() -> new ValidationException(new CustomError("id", "Ranking with id " + id + " not found")));
    }

    @Override
    public List<Ranking> getRankingByCompetitionCode(String code) throws ValidationException {
        if (rankingRepository.findByCompetition_Code(code).isEmpty())
            throw new ValidationException(new CustomError("code", "Ranking with competition code " + code + " not found"));
        return rankingRepository.findByCompetition_Code(code);
    }

    @Override
    public List<Ranking> getRankingByCompetitionId(Long id) throws ValidationException {
        if (rankingRepository.findByCompetition_IdOrderByRankAsc(id).isEmpty())
            throw new ValidationException(new CustomError("id", "Ranking with competition id " + id + " not found"));
        return rankingRepository.findByCompetition_IdOrderByRankAsc(id);
    }

    @Override
    public Optional<Ranking> getRankingByMemberAndCompetition(Member member, Competition competition) throws ValidationException {
        return rankingRepository.findByMemberAndCompetition(member, competition);
    }

    public void updateRankings(String code) {
        List<Ranking> rankings = rankingRepository.findByCompetition_Code(code);

        rankings.sort(Comparator.comparing(Ranking::getScore).reversed());

        int rank = 1;
        for (Ranking ranking : rankings) {
            ranking.setRank(rank++);
            rankingRepository.save(ranking);
        }
    }
}
