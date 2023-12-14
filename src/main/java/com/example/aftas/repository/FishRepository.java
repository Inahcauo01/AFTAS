package com.example.aftas.repository;

import com.example.aftas.domain.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {
    Optional<Fish> findByNameIgnoreCase(String name);

    @Query("SELECT f.name FROM Fish f")
    List<String> findAllFishNames();
}
