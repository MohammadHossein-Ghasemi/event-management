package co.muhu.eventManagement.mappers.member;

import co.muhu.eventManagement.entity.Member;
import co.muhu.eventManagement.mappers.event.EventMapper;
import co.muhu.eventManagement.model.EventDto;
import co.muhu.eventManagement.model.MemberDto;
import co.muhu.eventManagement.model.MemberRegistrationDto;

import java.util.List;

public class MemberMapper {
    public static Member memberRegistrationDtoToMember(MemberRegistrationDto memberRegistrationDto) {
        if(memberRegistrationDto==null){
            return new Member();
        }
        return Member.builder()
                .email(memberRegistrationDto.getEmail())
                .firstName(memberRegistrationDto.getFirstName())
                .lastName(memberRegistrationDto.getLastName())
                .phoneNumber(memberRegistrationDto.getPhoneNumber())
                .build();
    }

    public static MemberDto memberToMemberDto(Member member){
        if (member==null){
            return new MemberDto();
        }
        List<EventDto> organizedEvents = member
                .getOrganizedEvents()
                .stream()
                .map(EventMapper::eventToEventDto)
                .toList();

        List<EventDto> participatedEvents = member
                .getParticipatedEvents()
                .stream()
                .map(EventMapper::eventToEventDto)
                .toList();
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .phoneNumber(member.getPhoneNumber())
                .createdDate(member.getCreatedDate())
                .updatedDate(member.getUpdatedDate())
                .organizedEvents(organizedEvents)
                .participatedEvents(participatedEvents)
                .build();
    }
}
