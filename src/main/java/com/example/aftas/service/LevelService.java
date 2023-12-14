package com.example.aftas.service;

import com.example.aftas.domain.Fish;
import com.example.aftas.domain.Level;
import com.example.aftas.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LevelService {
    public List<Level> findAll();
    public Level save(Level level) throws ValidationException;
    public Level update(Level level) throws ValidationException;
    public Optional<Level> findById(Long id) throws ValidationException;
    public void delete(Long id) throws ValidationException;

    public List<Level> saveAll(List<Level> levelList) throws ValidationException;
}
