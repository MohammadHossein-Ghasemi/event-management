package co.muhu.eventManagement.mappers.participant;

import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.model.ParticipantRegistrationDto;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapperImpl implements ParticipantMapper {
    @Override
    public Participant participantRegistrationDtoToParticipant(ParticipantRegistrationDto participantRegistrationDto) {
        return Participant.builder()
                .name(participantRegistrationDto.getName())
                .email(participantRegistrationDto.getEmail())
                .phone(participantRegistrationDto.getPhone())
                .build();
    }
}
