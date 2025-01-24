package co.muhu.eventManagement.mappers.member;

import co.muhu.eventManagement.entity.Member;
import co.muhu.eventManagement.model.MemberRegistrationDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapperImpl implements MemberMapper {
    @Override
    public Member memberRegistrationDtoToMember(MemberRegistrationDto memberRegistrationDto) {
        return Member.builder()
                .email(memberRegistrationDto.getEmail())
                .firstName(memberRegistrationDto.getFirstName())
                .lastName(memberRegistrationDto.getLastName())
                .phoneNumber(memberRegistrationDto.getPhoneNumber())
                .build();
    }
}
