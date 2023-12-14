package com.example.aftas.repository;

import com.example.aftas.domain.Competition;
import com.example.aftas.domain.Fish;
import com.example.aftas.domain.Hunting;
import com.example.aftas.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HuntingRepository extends JpaRepository<Hunting, Long> {

    boolean existsByMemberAndCompetitionAndFish(Member member, Competition competition, Fish fish);

    Hunting findByMemberAndCompetitionAndFish(Member member, Competition competition, Fish fish);
}
