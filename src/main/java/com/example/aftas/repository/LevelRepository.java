package com.example.aftas.repository;

import com.example.aftas.domain.Fish;
import com.example.aftas.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findByDescription(String description);

    Optional<Level> findByPoints(Integer points);

    Optional<Level> findByDescriptionIgnoreCase(String description);
}
