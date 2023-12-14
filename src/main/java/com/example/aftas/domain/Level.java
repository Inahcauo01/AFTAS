package com.example.aftas.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Level {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Column(unique = true)
    private String description;

    @Column(unique = true)
    private Integer points;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private List<Fish> fishes;
}
