package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Member;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.MemberDto;
import co.muhu.eventManagement.model.MemberRegistrationDto;
import co.muhu.eventManagement.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    public static final String MEMBER_PATH ="/api/v1/member";
    public static final String MEMBER_PATH_ID = "/api/v1/member/{memberId}";
    public static final String MEMBER_PATH_EMAIL = "/api/v1/member/email";
    private final MemberService memberService;

    @GetMapping(value = MEMBER_PATH)
    public ResponseEntity<List<MemberDto>> getAllMembers(){
        List<MemberDto> memberList = memberService.getAllMembers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,MEMBER_PATH)
                .body(memberList);
    }

    @GetMapping(value = MEMBER_PATH_ID)
    public ResponseEntity<MemberDto> getMemberById(@PathVariable Long memberId){
        MemberDto foundedMember=memberService.getMemberById(memberId)
                .orElseThrow(()->new ResourceNotFoundException("There is no Member wit this id : "+memberId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,MEMBER_PATH+"/"+memberId)
                .body(foundedMember);
    }

    @PostMapping(value = MEMBER_PATH)
    public ResponseEntity<MemberDto> saveMember (@Validated @RequestBody MemberRegistrationDto memberRegistrationDto){
        MemberDto savedMember = memberService.createMember(memberRegistrationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION,MEMBER_PATH)
                .body(savedMember);
    }

    @PutMapping(value = MEMBER_PATH_ID)
    public ResponseEntity<MemberDto> updateMemberById(@Validated @RequestBody Member member,
                                               @PathVariable Long memberId){
        MemberDto updatedMember=memberService.updateMemberById(memberId,member)
                .orElseThrow(()-> new ResourceNotFoundException("There is no Member wit this id : "+memberId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,MEMBER_PATH+"/"+memberId)
                .body(updatedMember);
    }

    @DeleteMapping(value = MEMBER_PATH_ID)
    public ResponseEntity<?> deleteMemberById (@PathVariable Long memberId){
        boolean isDeleted = memberService.deleteMemberById(memberId);
        if (!isDeleted){
            throw new ResourceNotFoundException("There is no Member wit this id : "+memberId);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.LOCATION,MEMBER_PATH+"/"+memberId)
                .build();
    }

    @GetMapping(value = MEMBER_PATH_EMAIL)
    public ResponseEntity<MemberDto> getMemberByMEmail(@RequestParam String memberEmail, HttpServletRequest request){
        MemberDto foundedMember= memberService.getMemberByEmail(memberEmail)
               .orElseThrow(()->new ResourceNotFoundException("There is no Member wit this email : "+memberEmail));

       return ResponseEntity
               .status(HttpStatus.OK)
               .header(HttpHeaders.LOCATION,request.getServletPath())
               .body(foundedMember);
    }

    @DeleteMapping(MEMBER_PATH_EMAIL)
    public ResponseEntity<?> deleteMemberByEmail(@RequestParam String memberEmail, HttpServletRequest request){
        boolean isDeleted = memberService.deleteMemberByEmail(memberEmail);
        if (!isDeleted){
            throw new ResourceNotFoundException("There is no Member wit this email : "+memberEmail);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.LOCATION,request.getServletPath())
                .build();
    }

    @PutMapping(MEMBER_PATH_EMAIL)
    public ResponseEntity<MemberDto> updateMemberByEmail(@RequestParam String memberEmail,
                                                      @Validated @RequestBody Member member,
                                                      HttpServletRequest request){
        MemberDto updatedMember=memberService.updateMemberByEmail(memberEmail,member)
                .orElseThrow(()->new ResourceNotFoundException("There is no Member wit this email : "+memberEmail));

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,request.getServletPath())
                .body(updatedMember);
    }
}
