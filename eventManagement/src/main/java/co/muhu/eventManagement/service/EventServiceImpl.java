package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Optional<Event> updateEvent(Long id, Event event) {
        AtomicReference<Optional<Event>> foundedEvent = new AtomicReference<>() ;

        eventRepository.findById(id).ifPresentOrElse( existingEvent->{
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
            foundedEvent.set(Optional.of(eventRepository.save(event)));
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
