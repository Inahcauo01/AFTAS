package com.example.aftas.service;

import com.example.aftas.domain.Member;
import com.example.aftas.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface MemberService {
    public List<Member> getAllMembers();
    public Optional<Member> getMemberByNum(Integer num);
    public Member save(Member member) throws ValidationException;
    public Member update(Member member) throws ValidationException;
    public void delete(Long id) throws ValidationException;
    public Optional<Member> getMemberById(Long id) throws ValidationException;

    public List<Member> findByMembershipNumberOrNameOrFamilyName(String searchTerm) throws ValidationException;

    boolean existsByNumber(Integer num);

    Optional<Member> findByNum(Integer num);

    List<Member> getAllMembers(Integer pageNo, Integer pageSize);
}
