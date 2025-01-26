package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.model.EventDto;
import co.muhu.eventManagement.model.EventRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<EventDto> getAllEvents();
    Optional<EventDto> getEventById(Long id);
    EventDto createEvent(EventRegistrationDto event);
    Optional<EventDto> updateEvent(Long id,Event event);
    boolean deleteEventById(Long id);
}
