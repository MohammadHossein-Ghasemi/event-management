package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<Member> getAllMembers();
    Optional<Member> getMemberById(Long id);
    Member createMember(Member member);
    Optional<Member> updateMemberById(Long id, Member member);
    boolean deleteMemberById(Long id);
    Optional<Member> getMemberByEmail(String email);
    boolean deleteMemberByEmail(String email);
    Optional<Member> updateMemberByEmail(String email, Member member);
}
