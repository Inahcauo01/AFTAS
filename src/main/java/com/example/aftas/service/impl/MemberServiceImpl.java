package com.example.aftas.service.impl;

import com.example.aftas.domain.Member;
import com.example.aftas.repository.MemberRespository;
import com.example.aftas.service.MemberService;
import com.example.aftas.utils.CustomError;
import com.example.aftas.utils.ValidationException;
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
    public Optional<Member> getMemberByNum(Integer num) {
        return memberRespository.findByNum(num);
    }

    @Override
    public Optional<Member> getMemberById(Long id) throws ValidationException {
        if (isMemberNotExist(id))
            throw new ValidationException(new CustomError("ID","member with id " + id + " not found"));
        return memberRespository.findById(id);
    }

    @Override
    public Member save(Member member) throws ValidationException {
        Optional<Member> optionalLocationName = memberRespository.findByIdentityNumberAndIdentityDocument(member.getIdentityNumber(), member.getIdentityDocument());
        if(optionalLocationName.isPresent())
            throw new ValidationException(new CustomError("identityDocument","identityDocument is already exists"));
        return memberRespository.save(member);
    }


    @Override
    public Member update(Member member) {
        if (isMemberNotExist(member.getId()))
            throw new RuntimeException("Member with id " + member.getId() + " not found");
        return memberRespository.save(member);
    }

    @Override
    public void delete(Long id) {
        if (isMemberNotExist(id))
            throw new RuntimeException("Member with id " + id + " not found");
        memberRespository.deleteById(id);
    }

    private boolean isMemberNotExist(Long id) {
        return !memberRespository.existsById(id);
    }

}
