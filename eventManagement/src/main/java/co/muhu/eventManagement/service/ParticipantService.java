package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.model.ParticipantDto;
import co.muhu.eventManagement.model.ParticipantRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {
    List<ParticipantDto> getAllParticipants();
    Optional<ParticipantDto> getParticipantById(Long id);
    ParticipantDto createParticipant(ParticipantRegistrationDto participantRegistrationDto);
    Optional<ParticipantDto> updateParticipant(Long id, Participant participant);
    boolean deleteParticipantById(Long id);
    List<ParticipantDto> getParticipantsByEventId(Long eventId);
}
