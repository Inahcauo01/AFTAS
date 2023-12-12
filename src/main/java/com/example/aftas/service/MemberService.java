package com.example.aftas.service;

import com.example.aftas.domain.Member;
import com.example.aftas.utils.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MemberService {
    public List<Member> getAllMembers();
    public Optional<Member> getMemberByNum(Integer num);
    public Member save(Member member) throws ValidationException;
    public Member update(Member member) throws ValidationException;
    public void delete(Long id);
    public Optional<Member> getMemberById(Long id) throws ValidationException;
}
