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
    public List<Member> findByMembershipNumberOrNameOrFamilyName(String searchTerm) throws ValidationException {
        List<Member> members = memberRespository.findByNumOrNameOrFamilyName(searchTerm);
        if(members == null)
            throw new ValidationException(new CustomError("searchTerm","No member found"));
        return members;
    }

    @Override
    public boolean existsByNumber(Integer num) {
        return memberRespository.existsByNum(num);
    }

    @Override
    public Optional<Member> findByNum(Integer num) {
        return memberRespository.findByNum(num);
    }

    @Override
    public Member save(Member member) throws ValidationException {
        Optional<Member> existingMember = memberRespository.findByNum(member.getNum());
        if (existingMember.isPresent())
            throw new ValidationException(new CustomError("num", "Member with this num already exists"));

        Optional<Member> membre = memberRespository.findByIdentityNumberAndIdentityDocument(member.getIdentityNumber(), member.getIdentityDocument());
        if(membre.isPresent())
            throw new ValidationException(new CustomError("identityDocument","identityDocument is already exists"));
        return memberRespository.save(member);
    }


    @Override
    public Member update(Member member) throws ValidationException{
        if (isMemberNotExist(member.getId()))
            throw new ValidationException(new CustomError("ID","member with id " + member.getId() + " not found"));
        return memberRespository.save(member);
    }

    @Override
    public void delete(Long id) throws ValidationException{
        if (isMemberNotExist(id))
            throw new ValidationException(new CustomError("ID","member with id " + id + " not found"));
        memberRespository.deleteById(id);
    }

    private boolean isMemberNotExist(Long id) {
        return !memberRespository.existsById(id);
    }

}
