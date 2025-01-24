package co.muhu.eventManagement.mappers.member;

import co.muhu.eventManagement.entity.Member;
import co.muhu.eventManagement.model.MemberRegistrationDto;

public interface MemberMapper {
    Member memberRegistrationDtoToMember(MemberRegistrationDto memberRegistrationDto);
}
