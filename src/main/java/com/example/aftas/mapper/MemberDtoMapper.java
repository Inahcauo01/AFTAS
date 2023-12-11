package com.example.aftas.mapper;

import com.example.aftas.domain.Member;
import com.example.aftas.domain.enums.IdentityDocumentType;
import com.example.aftas.dto.MemberDto;

public class MemberDtoMapper {
    public static MemberDto toDto(Member member){
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .familyName(member.getFamilyName())
                .accessionDate(member.getAccessionDate())
                .nationality(member.getNationality())
                .identityNumber(member.getIdentityNumber())
                .identityDocument(member.getIdentityDocument().name())
                .build();
    }


    public static Member toEntity(MemberDto memberDto){

        return Member.builder()
                .id(memberDto.getId())
                .name(memberDto.getName())
                .familyName(memberDto.getFamilyName())
                .accessionDate(memberDto.getAccessionDate())
                .nationality(memberDto.getNationality())
                .identityNumber(memberDto.getIdentityNumber())
                .identityDocument(IdentityDocumentType.valueOf(memberDto.getIdentityDocument()))
                .build();
    }
}
