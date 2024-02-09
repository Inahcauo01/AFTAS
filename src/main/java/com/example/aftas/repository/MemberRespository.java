package com.example.aftas.repository;

import com.example.aftas.domain.Member;
import com.example.aftas.domain.enums.IdentityDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRespository extends JpaRepository<Member, Long> {
    Optional<Member> findByNum(Integer num);

    Optional<Member> findByIdentityNumberAndIdentityDocument(String identityNumber, IdentityDocumentType identityDocument);

    Optional<Member> findByName(String name);

    // search by num or name or family name
    @Query(value = "SELECT * FROM member WHERE num = :searchTerm OR name LIKE %:searchTerm% OR family_name LIKE %:searchTerm%",
            nativeQuery = true)
    List<Member> findByNumOrNameOrFamilyName(@Param("searchTerm") String searchTerm);

    boolean existsByNum(Integer num);

    Optional<Member> findByUsername(String username);
}
