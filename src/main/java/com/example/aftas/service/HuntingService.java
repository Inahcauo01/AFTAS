package com.example.aftas.service;

import com.example.aftas.domain.Hunting;
import com.example.aftas.dto.HuntingDto;
import com.example.aftas.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface HuntingService {
    public Hunting save(Hunting hunting) throws ValidationException;
    public Hunting update(Hunting hunting) throws ValidationException;
    public List<Hunting> getAllHuntings();
    public Optional<Hunting> getHuntingById(Long id) throws ValidationException;
    public void deleteHuntingById(Long id) throws ValidationException;
}
