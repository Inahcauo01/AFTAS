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
    }


    @Override
    public Competition update(Competition competition) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Competition getCompetitionById(Long id) {
        return null;
    }

    private void validateCompetition(Competition competition) throws ValidationException {
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

    }
}
