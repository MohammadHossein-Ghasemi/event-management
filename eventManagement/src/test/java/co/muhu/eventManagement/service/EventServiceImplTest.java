package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class EventServiceImplTest {
    private EventServiceImpl eventServiceTest;

    @Mock
    private EventRepository repositoryMock;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        eventServiceTest=new EventServiceImpl(repositoryMock);
    }

    @AfterEach
    void afterEach() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllEvents() {
        eventServiceTest.getAllEvents();
        verify(repositoryMock).findAll();
    }

    @Test
    void getEventById() {
        long eventId=1;
        eventServiceTest.getEventById(eventId);
        verify(repositoryMock).findById(eventId);
    }

    @Test
    void createEvent() {
        Event newEvent = new Event();
        eventServiceTest.createEvent(newEvent);
        verify(repositoryMock).save(newEvent);
    }

    @Test
    void updateEvent() {
        Event exitingEvent = Event.builder().id((long)1).build();
        Event updatedEvent = Event.builder() .id(1L) .name("Updated Event").build();

        when(repositoryMock.findById(exitingEvent.getId())).thenReturn(Optional.of(exitingEvent));
        when(repositoryMock.save(updatedEvent)).thenReturn(updatedEvent);

        eventServiceTest.updateEvent(updatedEvent.getId(),updatedEvent);

        verify(repositoryMock).save(updatedEvent);
    }
    @Test
    void updateEventWhenEventNotPresent() {
        Event exitingEvent = Event.builder().id((long)1).build();
        Event updatedEvent = Event.builder() .id(1L) .name("Updated Event").build();

        when(repositoryMock.findById(exitingEvent.getId())).thenReturn(Optional.empty());


        Optional<Event> result = eventServiceTest.updateEvent(updatedEvent.getId(), updatedEvent);

        assertThat(result).isNotPresent();
    }

    @Test
    void deleteEventById() {
        long eventId = 1;

        when(repositoryMock.existsById(eventId)).thenReturn(true);

        eventServiceTest.deleteEventById(eventId);

        verify(repositoryMock).deleteById(eventId);
    }

    @Test
    void deleteEventByIdWhenEventNotPresent() {
        long eventId = 1;

        when(repositoryMock.existsById(eventId)).thenReturn(false);

        boolean result=eventServiceTest.deleteEventById(eventId);

        assertThat(result).isFalse();
    }
}