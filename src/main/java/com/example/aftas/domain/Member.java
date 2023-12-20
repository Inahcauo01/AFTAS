package com.example.aftas.domain;

import com.example.aftas.domain.enums.IdentityDocumentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"identityDocument", "identityNumber"})})
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Integer num;
    private String name;
    private String familyName;
    private LocalDate accessionDate;
    private String nationality;

    @Enumerated(EnumType.STRING)
    private IdentityDocumentType identityDocument;
    private String identityNumber;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Ranking> rankings;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Hunting> huntings;
}
