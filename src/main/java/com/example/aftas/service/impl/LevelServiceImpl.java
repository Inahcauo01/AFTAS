package com.example.aftas.service.impl;

import com.example.aftas.domain.Fish;
import com.example.aftas.domain.Level;
import com.example.aftas.repository.LevelRepository;
import com.example.aftas.service.LevelService;
import com.example.aftas.utils.CustomError;
import com.example.aftas.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;

    @Override
    public List<Level> findAll() {
        return levelRepository.findAll();
    }

    @Override
    public Level save(Level level) throws ValidationException {
        validateLevel(level);
        return levelRepository.save(level);
    }

    private void validateLevel(Level level) throws ValidationException {
        Optional<Level> levelOptional = levelRepository.findByDescription(level.getDescription());
        if (levelOptional.isPresent()) {
            throw new ValidationException(new CustomError("description", "Description already exists"));
        }

        levelOptional = levelRepository.findByPoints(level.getPoints());
        if (levelOptional.isPresent()) {
            throw new ValidationException(new CustomError("points", "Points already exists"));
        }
    }

    @Override
    public Level update(Level level) {
        return levelRepository.save(level);
    }

    @Override
    public Optional<Level> findById(Long id) throws ValidationException {
        if (!levelRepository.existsById(id))
            throw new ValidationException(new CustomError("id", "Level not found"));
        return levelRepository.findById(id);
    }

    @Override
    public void delete(Long id) throws ValidationException {
        if (!levelRepository.existsById(id))
            throw new ValidationException(new CustomError("id", "Level not found"));
        levelRepository.deleteById(id);
    }

    @Override
    public List<Level> saveAll(List<Level> levelList) throws ValidationException {
        Level prevLevel = null;
        for (Level level : levelList) {
            Optional<Level> levelOptional = levelRepository.findByDescription(level.getDescription());
            if (levelOptional.isPresent())
                throw new ValidationException(new CustomError("description", "Level with this description already exists"));

            levelOptional = levelRepository.findByPoints(level.getPoints());
            if (levelOptional.isPresent())
                throw new ValidationException(new CustomError("points", "Level with this points already exists"));

            if (prevLevel != null && level.getPoints() < prevLevel.getPoints()) {
                throw new ValidationException(new CustomError("points", "Level points must be in ascending order"));
            }

            prevLevel = level;
        }
        return levelRepository.saveAll(levelList);
    }
}
