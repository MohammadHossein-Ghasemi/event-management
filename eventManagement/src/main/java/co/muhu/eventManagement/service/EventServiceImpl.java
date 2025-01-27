package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.*;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.mappers.event.EventMapper;
import co.muhu.eventManagement.model.*;
import co.muhu.eventManagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
       Member member = checkingMemberValidate(event)
               .orElseThrow(()->new ResourceNotFoundException("Invalid member. Register the member first."));
       Organizer organizer = checkingOrganizerValidate(event)
               .orElseThrow(()->new ResourceNotFoundException("Invalid organizer. Register the organizer first."));
       Venue venue = checkingVenueValidate(event)
               .orElseThrow(()->new ResourceNotFoundException("Invalid venue. Register the venue first."));
        Set<Participant> participantSet = checkingParticipantValidate(event)
                .orElseThrow(()->new ResourceNotFoundException("Invalid participant. Register the participant first"));

        Event mappedEvent = EventMapper.eventRegistrationDtoToEvent(event);
        mappedEvent.setMember(member);
        mappedEvent.setOrganizer(organizer);
        mappedEvent.setVenue(venue);
        mappedEvent.setParticipantSet(participantSet);
        return EventMapper.eventToEventDto(eventRepository.save(mappedEvent));
    }

    private Optional<Member> checkingMemberValidate(EventRegistrationDto eventRegistrationDto){
        if ((eventRegistrationDto.getMember()==null)||
                (eventRegistrationDto.getMember().getId()==null)||
                (!memberRepository.existsById(eventRegistrationDto.getMember().getId()))){
            return Optional.empty();
        }
        return memberRepository.findById(eventRegistrationDto.getMember().getId());
    }
    private Optional<Organizer> checkingOrganizerValidate(EventRegistrationDto eventRegistrationDto){
        if ((eventRegistrationDto.getOrganizer()==null)||
                (eventRegistrationDto.getOrganizer().getId()==null)||
                (!organizerRepository.existsById(eventRegistrationDto.getOrganizer().getId()))){
            return Optional.empty();
        }
        return organizerRepository.findById(eventRegistrationDto.getOrganizer().getId());
    }
    private Optional<Venue> checkingVenueValidate(EventRegistrationDto eventRegistrationDto){
        if ((eventRegistrationDto.getVenue()==null)||
                (eventRegistrationDto.getVenue().getId()==null)||
                (!venueRepository.existsById(eventRegistrationDto.getVenue().getId()))){
            return Optional.empty();
        }
        return venueRepository.findById(eventRegistrationDto.getVenue().getId());
    }
    private Optional<Set<Participant>> checkingParticipantValidate(EventRegistrationDto eventRegistrationDto){
        if (eventRegistrationDto.getParticipantSet()==null){
            return Optional.empty();
        }
        boolean isInvalided = eventRegistrationDto.getParticipantSet()
                .stream()
                .anyMatch(participant ->
                        (participant.getId() == null || !participantRepository.existsById(participant.getId())));
        if (isInvalided){
            return Optional.empty();
        }
        return Optional.of(eventRegistrationDto.getParticipantSet());
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
