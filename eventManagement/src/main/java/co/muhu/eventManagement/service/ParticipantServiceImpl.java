package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.mappers.participant.ParticipantMapper;
import co.muhu.eventManagement.model.ParticipantDto;
import co.muhu.eventManagement.model.ParticipantRegistrationDto;
import co.muhu.eventManagement.repository.EventRepository;
import co.muhu.eventManagement.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipantDto> getAllParticipants() {

        return participantRepository.findAll()
                .stream()
                .map(ParticipantMapper::participantToParticipantDto)
                .toList();
    }

    @Override
    public Optional<ParticipantDto> getParticipantById(Long id) {

        return participantRepository.findById(id)
                .map(ParticipantMapper::participantToParticipantDto);
    }

    @Override
    public ParticipantDto createParticipant(ParticipantRegistrationDto participantRegistrationDto) {
        Participant participant = ParticipantMapper.participantRegistrationDtoToParticipant(participantRegistrationDto);
        return ParticipantMapper.participantToParticipantDto(participantRepository.save(participant));
    }

    @Override
    public Optional<ParticipantDto> updateParticipant(Long id, Participant participant) {
        AtomicReference<Optional<ParticipantDto>> foundedParticipant = new AtomicReference<>();

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
                    foundedParticipant.set(Optional.of(ParticipantMapper.participantToParticipantDto(participantRepository.save(updatedParticipant))));
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
    public List<ParticipantDto> getParticipantsByEventId(Long eventId) {
        if (!eventRepository.existsById(eventId)){
            throw new ResourceNotFoundException("There is no event with this id : "+eventId);
        }
        return participantRepository.findAllByEventSetId(eventId)
                .stream()
                .map(ParticipantMapper::participantToParticipantDto)
                .toList();
    }
}
