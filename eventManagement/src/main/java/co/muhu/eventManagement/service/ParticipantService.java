package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Participant;

import java.util.List;
import java.util.Optional;

public interface ParticipantService {
    List<Participant> getAllParticipants();
    Optional<Participant> getParticipantById(Long id);
    Participant createParticipant(Participant participant);
    Optional<Participant> updateParticipant(Long id, Participant participant);
    boolean deleteParticipantById(Long id);
    List<Participant> getParticipantsByEventId(Long eventId);
}
