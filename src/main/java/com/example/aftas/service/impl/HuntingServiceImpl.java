package com.example.aftas.service.impl;

import com.example.aftas.domain.*;
import com.example.aftas.repository.HuntingRepository;
import com.example.aftas.service.HuntingService;
import com.example.aftas.service.RankingService;
import com.example.aftas.utils.CustomError;
import com.example.aftas.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HuntingServiceImpl implements HuntingService {
    private final HuntingRepository huntingRepository;
    private final FishServiceImpl fishService;
    private final MemberServiceImpl memberService;
    private final CompetitionServiceImpl competitionService;
    private final RankingService rankingService;


    @Override
    public List<Hunting> getAllHuntings() {
        return huntingRepository.findAll();
    }

    @Override
    public Hunting save(Hunting hunting) throws ValidationException {
        validateHunting(hunting);
        Hunting savedHunting;

        if (huntingRepository.existsByMemberAndCompetitionAndFish(hunting.getMember(), hunting.getCompetition(), hunting.getFish())) {
            Hunting hunting1 = huntingRepository.findByMemberAndCompetitionAndFish(hunting.getMember(), hunting.getCompetition(), hunting.getFish());
            hunting1.setNumberOfFish(hunting1.getNumberOfFish() + 1);
            savedHunting = huntingRepository.save(hunting1);
        }else {
            savedHunting = huntingRepository.save(hunting);
        }

        Optional<Ranking> rankingOptional = rankingService.getRankingByMemberAndCompetition(hunting.getMember(), hunting.getCompetition());
        Integer points = hunting.getFish().getLevel().getPoints();

        if (rankingOptional.isPresent()) {
            Ranking ranking = rankingOptional.get();
            ranking.setScore(ranking.getScore() + points);
            rankingService.update(ranking);
        } else {
            rankingService.save(
                    Ranking.builder()
                    .member(hunting.getMember())
                    .competition(hunting.getCompetition())
                    .score(points)
                    .build()
            );
        }
        return savedHunting;
    }

    private void validateHunting(Hunting hunting) throws ValidationException {
        Optional<Competition> optionalCompetition = competitionService.findByCode(hunting.getCompetition().getCode());
        Optional<Member> optionalMember = memberService.findByNum(hunting.getMember().getNum());
        Optional<Fish> optionalFish = fishService.findByName(hunting.getFish().getName());
        LocalDateTime now = LocalDateTime.now();

        if (optionalMember.isEmpty())
            throw new ValidationException(new CustomError("member", "member not found"));

        Member member = optionalMember.get();


        if (optionalCompetition.isEmpty())
            throw new ValidationException(new CustomError("competition", "Invalid competition code"));

        Competition competition = optionalCompetition.get();


        if (optionalFish.isEmpty())
            throw new ValidationException(new CustomError("fish", "Invalid fish name"));

        Fish fish = optionalFish.get();


        hunting.setMember(member);
        hunting.setCompetition(competition);
        hunting.setFish(fish);

        // Check if the competition date is before 24 hours from now
        if (competition.getDate().isBefore(ChronoLocalDate.from(now.minusHours(24))))
            throw new ValidationException(new CustomError("competition date", "Competition date must be at least 24 hours from now"));


        // Check if the competition date is after 24 hours from now
//        if (optionalCompetition.get().getDate().isAfter(ChronoLocalDate.from(now.plusHours(24)))) {
//            throw new ValidationException(new CustomError("competition date", "Competition date must be within the next 24 hours of competition start time"));
//        }


        Optional<Ranking> rankingOptional = rankingService.getRankingByMemberAndCompetition(member, competition);

        if (rankingOptional.isEmpty())
            throw new ValidationException(new CustomError("member", "member is not registered in the competition"));


        List<Ranking> rankings = rankingService.getRankingByCompetitionCode(competition.getCode());
        if (rankings.size() >= competition.getNumberOfParticipants())
            throw new ValidationException(new CustomError("competition", "competition is full"));

    }


    @Override
    public Hunting update(Hunting hunting) throws ValidationException {
        validateHunting(hunting);
        return huntingRepository.save(hunting);
    }


    @Override
    public Optional<Hunting> getHuntingById(Long id) throws ValidationException {
        huntingNotExistsById(id);
        return huntingRepository.findById(id);
    }

    @Override
    public void deleteHuntingById(Long id) throws ValidationException {
        huntingNotExistsById(id);
        huntingRepository.deleteById(id);
    }

    private void huntingNotExistsById(Long id) throws ValidationException {
        if (!huntingRepository.existsById(id)) {
            throw new ValidationException(new CustomError("hunting", "hunting not found"));
        }
    }
}
