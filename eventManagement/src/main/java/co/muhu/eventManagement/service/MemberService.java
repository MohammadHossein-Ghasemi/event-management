package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Member;
import co.muhu.eventManagement.model.MemberDto;
import co.muhu.eventManagement.model.MemberRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<MemberDto> getAllMembers();
    Optional<MemberDto> getMemberById(Long id);
    MemberDto createMember(MemberRegistrationDto memberRegistrationDto);
    Optional<MemberDto> updateMemberById(Long id, Member member);
    boolean deleteMemberById(Long id);
    Optional<MemberDto> getMemberByEmail(String email);
    boolean deleteMemberByEmail(String email);
    Optional<MemberDto> updateMemberByEmail(String email, Member member);
}
