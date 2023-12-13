package com.example.aftas.service.impl;

import com.example.aftas.domain.Fish;
import com.example.aftas.repository.FishRepository;
import com.example.aftas.service.FishService;
import com.example.aftas.utils.CustomError;
import com.example.aftas.utils.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FishServiceImpl implements FishService {

    private final FishRepository fishRepository;
    @Override
    public List<Fish> findAll() {
        return fishRepository.findAll();
    }

    @Override
    public Fish save(Fish fish) throws ValidationException {
        Optional<Fish> existingFish = fishRepository.findByNameIgnoreCase(fish.getName());
        if (existingFish.isPresent())
            throw new ValidationException(new CustomError("name", "Fish with this name already exists"));
        return fishRepository.save(fish);
    }

    @Override
    public Fish update(Fish fish) throws ValidationException {
        Optional<Fish> existingFish = fishRepository.findById(fish.getId());
        if (existingFish.isEmpty())
            throw new ValidationException(new CustomError("id", "Fish with this id does not exist"));
        return fishRepository.save(fish);
    }

    @Override
    public Fish findById(Long id) throws ValidationException {
        Optional<Fish> existingFish = fishRepository.findById(id);
        if (existingFish.isEmpty())
            throw new ValidationException(new CustomError("id", "Fish with this id does not exist"));
        return existingFish.get();
    }

    @Override
    public void delete(Long id) throws ValidationException {
        Optional<Fish> existingFish = fishRepository.findById(id);
        if (existingFish.isEmpty())
            throw new ValidationException(new CustomError("id", "Fish with this id does not exist"));
        fishRepository.delete(existingFish.get());
    }

    @Override
    public List<Fish> saveAll(List<Fish> fishList) throws ValidationException {
        // check if fish with same name already exists
        for (Fish fish : fishList) {
            Optional<Fish> existingFish = fishRepository.findByNameIgnoreCase(fish.getName());
            if (existingFish.isPresent())
                throw new ValidationException(new CustomError("name", "Fish with this name already exists"));
        }
        return fishRepository.saveAll(fishList);
    }
}
