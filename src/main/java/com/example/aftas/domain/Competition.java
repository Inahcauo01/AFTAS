package com.example.aftas.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private LocalDate date;
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    @Builder.Default
    private LocalTime endTime = LocalTime.parse("23:59");

    private Integer numberOfParticipants;
    private String location;
    private Double amount;

}
