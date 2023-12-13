package com.example.aftas.web.rest;

import com.example.aftas.domain.Member;
import com.example.aftas.dto.MemberDto;
import com.example.aftas.mapper.MemberDtoMapper;
import com.example.aftas.service.MemberService;
import com.example.aftas.utils.CustomError;
import com.example.aftas.utils.Response;
import com.example.aftas.utils.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Response<MemberDto>> create(@Valid @RequestBody MemberDto memberDto){
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

    @GetMapping("/{id}")
    public ResponseEntity<Response<MemberDto>> getMemberById(@PathVariable Long id) throws ValidationException {
        Response<MemberDto> response = new Response<>();
        Optional<Member> member = memberService.getMemberById(id);
        response.setResult(MemberDtoMapper.toDto(member.get()));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<MemberDto>> update(@PathVariable Long id, @Valid @RequestBody MemberDto memberDto){
        Response<MemberDto> response = new Response<>();
        Member member = MemberDtoMapper.toEntity(memberDto);
        try {
            response.setResult(MemberDtoMapper.toDto(memberService.update(member)));
            response.setMessage("Member has been updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ValidationException e) {
            response.setMessage("Member has not been updated");
            response.setErrors(List.of(e.getCustomError()));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<MemberDto>> delete(@PathVariable Long id) throws ValidationException {
        Response<MemberDto> response = new Response<>();
        memberService.delete(id);
        response.setMessage("Member has been deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/search")
    public ResponseEntity<Response<List<Member>>> findByMembershipNumberOrNameOrFamilyName(@RequestBody @Valid String searchTerm) throws ValidationException {
        List<Member> members = memberService.findByMembershipNumberOrNameOrFamilyName(searchTerm);
        Response<List<Member>> response;
        if (members.isEmpty()) {
            response = Response.<List<Member>>builder().message("No member found").errors(List.of(new CustomError("searchTerm", "No member found"))).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response = Response.<List<Member>>builder().message("Members found").result(members).errors(null).build();
        return ResponseEntity.ok(response);
    }

}
