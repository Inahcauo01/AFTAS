package com.example.aftas.service;

import com.example.aftas.domain.Fish;
import com.example.aftas.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public interface FishService {
    public List<Fish> findAll();
    public Fish save(Fish fish) throws ValidationException;
    public Fish update(Fish fish) throws ValidationException;
    public Fish findById(Long id) throws ValidationException;
    public void delete(Long id) throws ValidationException;

    public List<Fish> saveAll(List<Fish> fishList) throws ValidationException;

}
