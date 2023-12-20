package com.example.aftas.repository;

import com.example.aftas.domain.Competition;
import com.example.aftas.domain.Member;
import com.example.aftas.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    Optional<Ranking> findByMemberAndCompetition(Member member, Competition competition);

    List<Ranking> findByCompetition_Code(String code);


    List<Ranking> findByCompetition_IdOrderByRankAsc(Long id);

    Number countByCompetition(Competition competition);
}
