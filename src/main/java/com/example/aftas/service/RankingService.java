package com.example.aftas.service;

import com.example.aftas.domain.Competition;
import com.example.aftas.domain.Member;
import com.example.aftas.domain.Ranking;
import com.example.aftas.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RankingService {
    public List<Ranking> getAllRankings();
    public Ranking save(Ranking ranking) throws ValidationException;
    public Ranking update(Ranking ranking) throws ValidationException;
    public void deleteRankingById(Long id) throws ValidationException;
    public Ranking getRankingById(Long id) throws ValidationException;
    public List<Ranking> getRankingByCompetitionCode(String code) throws ValidationException;
    public List<Ranking> getRankingByCompetitionId(Long id) throws ValidationException;
    public Optional<Ranking> getRankingByMemberAndCompetition(Member member, Competition competition) throws ValidationException;

    public void updateRankings(String code);
}
