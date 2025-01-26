package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Member;
import co.muhu.eventManagement.mappers.member.MemberMapper;
import co.muhu.eventManagement.model.MemberDto;
import co.muhu.eventManagement.model.MemberRegistrationDto;
import co.muhu.eventManagement.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public List<MemberDto> getAllMembers() {

        return memberRepository.findAll()
                .stream()
                .map(MemberMapper::memberToMemberDto)
                .toList();
    }

    @Override
    public Optional<MemberDto> getMemberById(Long id)
    {

        return memberRepository.findById(id)
                .map(MemberMapper::memberToMemberDto);
    }

    @Override
    public MemberDto createMember(MemberRegistrationDto memberRegistrationDto) {
        Member member = MemberMapper.memberRegistrationDtoToMember(memberRegistrationDto);
        return MemberMapper.memberToMemberDto(memberRepository.save(member));
    }

    @Override
    public Optional<MemberDto> updateMemberById(Long id, Member member) {
        AtomicReference<Optional<MemberDto>> foundedMember =new AtomicReference<>();
        memberRepository.findById(id).ifPresentOrElse(
                updatedMember->{
                    updatedMember.setEmail(member.getEmail());
                    updatedMember.setLastName(member.getLastName());
                    updatedMember.setFirstName(member.getFirstName());
                    updatedMember.setPhoneNumber(member.getPhoneNumber());
                    updatedMember.setOrganizedEvents(member.getOrganizedEvents());
                    updatedMember.setParticipatedEvents(member.getParticipatedEvents());
                    foundedMember.set(Optional.of(MemberMapper.memberToMemberDto(memberRepository.save(updatedMember))));
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
    public Optional<MemberDto> getMemberByEmail(String email) {

        return memberRepository.findByEmail(email)
                .map(MemberMapper::memberToMemberDto);
    }

    @Override
    @Transactional
    public boolean deleteMemberByEmail(String email) {
        if (memberRepository.existsByEmail(email)){
            memberRepository.deleteByEmail(email);
            return true;
        }
        return false;
    }

    @Override
    public Optional<MemberDto> updateMemberByEmail(String email, Member member) {
        AtomicReference<Optional<MemberDto>> foundedMember =new AtomicReference<>();
        memberRepository.findByEmail(email).ifPresentOrElse(
                updatedMember->{
                    updatedMember.setLastName(member.getLastName());
                    updatedMember.setFirstName(member.getFirstName());
                    updatedMember.setPhoneNumber(member.getPhoneNumber());
                    updatedMember.setOrganizedEvents(member.getOrganizedEvents());
                    updatedMember.setParticipatedEvents(member.getParticipatedEvents());
                    foundedMember.set(Optional.of(MemberMapper.memberToMemberDto(memberRepository.save(updatedMember))));
                },
                ()->foundedMember.set(Optional.empty())
        );
        return foundedMember.get();
    }
}
