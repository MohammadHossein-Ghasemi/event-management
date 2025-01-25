package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.mappers.participant.ParticipantMapper;
import co.muhu.eventManagement.model.ParticipantRegistrationDto;
import co.muhu.eventManagement.repository.EventRepository;
import co.muhu.eventManagement.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final ParticipantMapper participantMapper;
    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @Override
    public Optional<Participant> getParticipantById(Long id) {

        return participantRepository.findById(id);
    }

    @Override
    public Participant createParticipant(ParticipantRegistrationDto participantRegistrationDto) {
        Participant participant = participantMapper.participantRegistrationDtoToParticipant(participantRegistrationDto);
        return participantRepository.save(participant);
    }

    @Override
    public Optional<Participant> updateParticipant(Long id, Participant participant) {
        AtomicReference<Optional<Participant>> foundedParticipant = new AtomicReference<>();

        participantRepository.findById(id).ifPresentOrElse(
                updatedParticipant->{
                    updatedParticipant.setName(participant.getName());
                    updatedParticipant.setEmail(participant.getEmail());
                    updatedParticipant.setPhone(participant.getPhone());
                    updatedParticipant.setRegistrationDate(participant.getRegistrationDate());
                    updatedParticipant.setStatus(participant.getStatus());
                    updatedParticipant.setEventSet(participant.getEventSet());
                    updatedParticipant.setTicketSet(participant.getTicketSet());
                    updatedParticipant.setFeedBackSet(participant.getFeedBackSet());
                    foundedParticipant.set(Optional.of(participantRepository.save(updatedParticipant)));
                },
                ()->foundedParticipant.set(Optional.empty())
        );

        return foundedParticipant.get();
    }

    @Override
    public boolean deleteParticipantById(Long id) {
        if (participantRepository.existsById(id)){
            participantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Participant> getParticipantsByEventId(Long eventId) {
        if (!eventRepository.existsById(eventId)){
            throw new ResourceNotFoundException("There is no event with this id : "+eventId);
        }
        return participantRepository.findAllByEventSetId(eventId);
    }
}
