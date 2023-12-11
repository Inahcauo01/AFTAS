package com.example.aftas.web.rest;

import com.example.aftas.domain.Member;
import com.example.aftas.dto.MemberDto;
import com.example.aftas.mapper.MemberDtoMapper;
import com.example.aftas.service.MemberService;
import com.example.aftas.utils.Response;
import com.example.aftas.utils.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<Response<List<MemberDto>>> getAllMembers(){
        Response<List<MemberDto>> response = new Response<>();
        List<MemberDto> memberList = new ArrayList<>();
        memberService.getAllMembers().stream().map(MemberDtoMapper::toDto).forEach(memberList::add);
        response.setResult(memberList);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<MemberDto>> addMember(@Valid @RequestBody MemberDto memberDto){
        Response<MemberDto> response = new Response<>();
        Member member = MemberDtoMapper.toEntity(memberDto);
        try {
            response.setResult(MemberDtoMapper.toDto(memberService.save(member)));
            response.setMessage("Member has been added successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ValidationException e) {
            response.setMessage("Member has not been added");
            response.setErrors(List.of(e.getCustomError()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


}
