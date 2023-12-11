package com.example.aftas.service.impl;

import com.example.aftas.domain.Member;
import com.example.aftas.repository.MemberRespository;
import com.example.aftas.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRespository memberRespository;


    @Override
    public List<Member> getAllMembers() {
        return memberRespository.findAll();
    }

    @Override
    public Optional<Member> getMemberById(Long id) {
        return memberRespository.findById(id);
    }

    @Override
    public Member save(Member member) {
        return memberRespository.save(member);
    }

    @Override
    public Member update(Member member) {
        if (!isMemberExist(member.getId()))
            throw new RuntimeException("Member with id " + member.getId() + " not found");
        return memberRespository.save(member);
    }

    @Override
    public void deleteMember(Long id) {
        if (!isMemberExist(id))
            throw new RuntimeException("Member with id " + id + " not found");
        memberRespository.deleteById(id);
    }

    private boolean isMemberExist(Long id) {
        return memberRespository.existsById(id);
    }

}
