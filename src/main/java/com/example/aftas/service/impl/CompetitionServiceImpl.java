package com.example.aftas.service.impl;

import com.example.aftas.domain.Competition;
import com.example.aftas.domain.Member;
import com.example.aftas.domain.Ranking;
import com.example.aftas.repository.CompetitionRepository;
import com.example.aftas.repository.MemberRespository;
import com.example.aftas.repository.RankingRepository;
import com.example.aftas.service.CompetitionService;
import com.example.aftas.utils.CustomError;
import com.example.aftas.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final MemberRespository memberRepository;
    private final RankingRepository rankingRepository;

    @Override
    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    @Override
    public Competition save(Competition competition) throws ValidationException {
        if (competition.getLocation() == null)
            throw new ValidationException(new CustomError("location", "Location must not be null"));

        competition.setCode(competition.getLocation().substring(0, 3).toLowerCase() + "-" + competition.getDate().format(DateTimeFormatter.ofPattern("yy-MM-dd")));
        validateCompetition(competition);
        return competitionRepository.save(competition);
    }


    public Ranking addMemberToCompetition(Long memberId, Long competitionId) throws ValidationException {
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        Optional<Competition> competitionOptional = competitionRepository.findById(competitionId);

        if (memberOptional.isPresent() && competitionOptional.isPresent()) {
            Member member = memberOptional.get();
            Competition competition = competitionOptional.get();

            validateAddMemberToCompetition(member, competition);

            Ranking ranking = Ranking.builder()
                    .member(member)
                    .competition(competition)
                    .build();
            return rankingRepository.save(ranking);
        } else {
            throw new ValidationException(new CustomError("member or competition", "member or competition not found"));
        }
    }

    private void validateAddMemberToCompetition(Member member, Competition competition) throws ValidationException {
        // check if member is already exists in the competition
        if (!rankingRepository.findByMemberAndCompetition(member, competition).isEmpty())
            throw new ValidationException(new CustomError("member", "Member is already exists in the competition"));

        //check if competition is already started
        if (competition.getDate().isBefore(LocalDate.now()))
            throw new ValidationException(new CustomError("competition", "Competition is already started"));

        //check if competition is full
        if (competition.getNumberOfParticipants() < rankingRepository.countByCompetition(competition).intValue())
            throw new ValidationException(new CustomError("competition", "Competition is full"));
    }


    @Override
    public Competition update(Competition competition) {
        return null;
    }

    @Override
    public void delete(Long id) throws ValidationException {
        if (!memberRepository.existsById(id))
            throw new ValidationException(new CustomError("member", "Member not found"));

        // Explicitly delete associated rankings
        memberRepository.findById(id).ifPresent(member -> {
            List<Ranking> rankings = member.getRankings();
            if (rankings != null && !rankings.isEmpty()) {
                rankingRepository.deleteAll(rankings);
            }
        });

        // Now, delete the member
        memberRepository.deleteById(id);
    }

    @Override
    public Competition getCompetitionById(Long id) {
        return null;
    }

    private void validateCompetition(Competition competition) throws ValidationException {
        // check if location is empty
        if (competition.getLocation().isEmpty())
            throw new ValidationException(new CustomError("location", "Location is required"));

        // check if competition code is already exists
        if (competitionRepository.findByCode(competition.getCode()).isPresent())
            throw new ValidationException(new CustomError("competition code", "Competition code is already exists"));

        // check if another competition is already exists in the same date
        if (!competitionRepository.findByDate(competition.getDate()).isEmpty())
            throw new ValidationException(new CustomError("competition date", "Another competition is already exists in this date"));

        // check if competition start date is at least 2 days from now
        if (competition.getDate().isBefore(LocalDate.now().plusDays(2)))
            throw new ValidationException(new CustomError("competition date", "Competition start date must be at least 2 days from now"));

        // check if competition start time is at least 4 hours before the end time (midnight)
        if (competition.getStartTime().isAfter(competition.getEndTime().minusHours(4)))
            throw new ValidationException(new CustomError("competition time", "Competition start time must be at least 4 hours before the end time"));

        // check if start time is after end time
        if (competition.getStartTime().isAfter(competition.getEndTime()))
            throw new ValidationException(new CustomError("competition time", "Competition start time must be before the end time"));
    }

    public boolean existsByCode(String code) {
        return competitionRepository.existsByCode(code);
    }

    public Optional<Competition> findByCode(String code) {
        return competitionRepository.findByCode(code);
    }
}
