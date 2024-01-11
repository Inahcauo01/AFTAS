package com.example.aftas.service.impl;

import com.example.aftas.domain.Competition;
import com.example.aftas.dto.CompetitionDto;
import com.example.aftas.repository.CompetitionRepository;
import com.example.aftas.service.CompetitionService;
import com.example.aftas.utils.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CompetitionServiceImplTest {

    @Mock
    private CompetitionRepository competitionRepository;
    @InjectMocks
    private CompetitionServiceImpl competitionService;

    private Competition competition;

    @BeforeEach
    public void setUp() {
        competition = Competition.builder()
                .location("New York")
                .date(LocalDate.of(2024, 12, 31))
                .startTime(LocalTime.parse("14:00"))
                .endTime(LocalTime.parse("23:59"))
                .build();
    }

    @Test
    void save() throws ValidationException {
        // Mock the competitionRepository to return an empty Optional
        when(competitionRepository.findByCode(competition.getCode())).thenReturn(Optional.empty());
        when(competitionRepository.findByDate(competition.getDate())).thenReturn(List.of());

        // Call the save method on the competitionService object
        competitionService.save(competition);

        // Verify the behavior of the save method
        Mockito.verify(competitionRepository, Mockito.times(1)).save(competition);
    }

    @Test
    void saveWithNullLocation() {
        // Set up a competition with a null location
        competition.setLocation(null);

        // Assert that calling save with a null location throws a ValidationException
        assertThrows(ValidationException.class, () -> competitionService.save(competition));
    }

    @Test
    void saveWhenCompetitionOnSameDateExists() throws ValidationException {
        // Mock the competitionRepository to return a non-empty list
        when(competitionRepository.findByDate(competition.getDate()))
                .thenReturn(List.of(createSampleCompetition()));

        assertThrows(ValidationException.class, () -> competitionService.save(competition));
    }



    @Test
    void saveWithEmptyLocation() {
        // Set up a competition with an empty location
        competition.setLocation("");

        // Assert that calling save with an empty location throws a ValidationException
        assertThrows(ValidationException.class, () -> competitionService.save(competition));
    }


    private Competition createSampleCompetition() {
        return Competition.builder()
                .id(1L)
                .code("ABC-24-12-31")
                .date(LocalDate.of(2024, 12, 31))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(19, 0))
                .numberOfParticipants(10)
                .location("Sample Location")
                .amount(50.0)
                .build();
    }
}