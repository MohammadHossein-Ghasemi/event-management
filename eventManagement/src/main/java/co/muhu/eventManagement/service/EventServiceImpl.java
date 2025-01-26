package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.mappers.event.EventMapper;
import co.muhu.eventManagement.model.EventDto;
import co.muhu.eventManagement.model.EventRegistrationDto;
import co.muhu.eventManagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;
    private final OrganizerRepository organizerRepository;
    private final VenueRepository venueRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public List<EventDto> getAllEvents() {

        return eventRepository.findAll()
                .stream()
                .map(EventMapper::eventToEventDto)
                .toList();
    }

    @Override
    public Optional<EventDto> getEventById(Long id) {

        return eventRepository.findById(id)
                .map(EventMapper::eventToEventDto);
    }

    @Override
    public EventDto createEvent(EventRegistrationDto event) {
        Event mappedEvent = EventMapper.eventRegistrationDtoToEvent(event);

        if (mappedEvent.getMember()==null||mappedEvent.getMember().getId() == null || !memberRepository.existsById(mappedEvent.getMember().getId())) {
            throw new ResourceNotFoundException("Invalid member. Register the member first.");
        }

        if (mappedEvent.getOrganizer()==null||mappedEvent.getOrganizer().getId() == null || !organizerRepository.existsById(mappedEvent.getOrganizer().getId())) {
            throw new ResourceNotFoundException("Invalid organizer. Register the organizer first.");
        }

        if (mappedEvent.getVenue()==null||mappedEvent.getVenue().getId() == null || !venueRepository.existsById(mappedEvent.getVenue().getId())) {
            throw new ResourceNotFoundException("Invalid venue. Register the venue first.");
        }

        if (mappedEvent.getParticipantSet()==null){
            throw new ResourceNotFoundException("Invalid participant. Register the participant first");
        }
        mappedEvent.getParticipantSet().forEach(participant -> {
            if (participant.getId() == null || !participantRepository.existsById(participant.getId())) {
                throw new ResourceNotFoundException("Invalid participant. Register the participant first: participantId=" + (participant == null ? "null" : participant.getId()));
            }
        });

        return EventMapper.eventToEventDto(eventRepository.save(mappedEvent));
    }


    @Override
    public Optional<EventDto> updateEvent(Long id, Event event) {
        AtomicReference<Optional<EventDto>> foundedEvent = new AtomicReference<>() ;

        eventRepository.findById(id).ifPresentOrElse( existingEvent-> {
            existingEvent.setName(event.getName());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setLocation(event.getLocation());
            existingEvent.setStartTime(event.getStartTime());
            existingEvent.setEndTime(event.getEndTime());
            existingEvent.setMember(event.getMember());
            existingEvent.setMemberset(event.getMemberset());
            existingEvent.setParticipantSet(event.getParticipantSet());
            existingEvent.setOrganizer(event.getOrganizer());
            existingEvent.setTicketSet(event.getTicketSet());
            existingEvent.setVenue(event.getVenue());
            existingEvent.setFeedBackSet(event.getFeedBackSet());
            foundedEvent.set(Optional.of(EventMapper.eventToEventDto(eventRepository.save(existingEvent))));
            },
                ()->{foundedEvent.set(Optional.empty());}
        );
        return foundedEvent.get();
    }

    @Override
    public boolean deleteEventById(Long id) {
        if (eventRepository.existsById(id)){
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
