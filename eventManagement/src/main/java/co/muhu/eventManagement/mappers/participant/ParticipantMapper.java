package co.muhu.eventManagement.mappers.participant;

import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.model.ParticipantRegistrationDto;

public interface ParticipantMapper {
    Participant participantRegistrationDtoToParticipant(ParticipantRegistrationDto participantRegistrationDto);
}
