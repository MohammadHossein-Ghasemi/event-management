package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> getAllEvents();
    Optional<Event> getEventById(Long id);
    Event createEvent(Event event);
    Optional<Event> updateEvent(Long id,Event event);
    boolean deleteEventById(Long id);
}
