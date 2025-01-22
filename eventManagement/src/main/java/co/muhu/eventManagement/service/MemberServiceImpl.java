package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Member;
import co.muhu.eventManagement.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getMemberById(Long id)
    {
        return memberRepository.findById(id);
    }

    @Override
    public Member createMember(Member member) {

        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> updateMemberById(Long id, Member member) {
        AtomicReference<Optional<Member>> foundedMember =new AtomicReference<>();
        memberRepository.findById(id).ifPresentOrElse(
                updatedMember->{
                    updatedMember.setEmail(member.getEmail());
                    updatedMember.setLastName(member.getLastName());
                    updatedMember.setFirstName(member.getFirstName());
                    updatedMember.setPhoneNumber(member.getPhoneNumber());
                    updatedMember.setOrganizedEvents(member.getOrganizedEvents());
                    updatedMember.setParticipatedEvents(member.getParticipatedEvents());
                    foundedMember.set(Optional.of(memberRepository.save(updatedMember)));
                },
                ()->foundedMember.set(Optional.empty())
        );
        return foundedMember.get();
    }

    @Override
    public boolean deleteMemberById(Long id) {
        if (memberRepository.existsById(id)){
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public boolean deleteMemberByEmail(String email) {
        if (memberRepository.existsByEmail(email)){
            memberRepository.deleteByEmail(email);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Member> updateMemberByEmail(String email, Member member) {
        AtomicReference<Optional<Member>> foundedMember =new AtomicReference<>();
        memberRepository.findByEmail(email).ifPresentOrElse(
                updatedMember->{
                    updatedMember.setLastName(member.getLastName());
                    updatedMember.setFirstName(member.getFirstName());
                    updatedMember.setPhoneNumber(member.getPhoneNumber());
                    updatedMember.setOrganizedEvents(member.getOrganizedEvents());
                    updatedMember.setParticipatedEvents(member.getParticipatedEvents());
                    foundedMember.set(Optional.of(memberRepository.save(updatedMember)));
                },
                ()->foundedMember.set(Optional.empty())
        );
        return foundedMember.get();
    }
}
