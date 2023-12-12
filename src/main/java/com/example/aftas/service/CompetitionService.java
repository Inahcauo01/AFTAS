package com.example.aftas.service;

import com.example.aftas.domain.Competition;
import com.example.aftas.repository.CompetitionRepository;
import com.example.aftas.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompetitionService {

    public List<Competition> getAllCompetitions();
    public Competition save(Competition competition) throws ValidationException;
    public Competition update(Competition competition);
    public void delete(Long id);
    public Competition getCompetitionById(Long id);
}
