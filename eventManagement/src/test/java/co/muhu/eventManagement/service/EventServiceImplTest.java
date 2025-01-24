package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.*;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.mappers.EventMapper;
import co.muhu.eventManagement.mappers.EventMapperImpl;
import co.muhu.eventManagement.model.EventRegistrationDto;
import co.muhu.eventManagement.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


class EventServiceImplTest {
    private EventServiceImpl eventServiceTest;

    @Mock
    private EventRepository eventRepositoryMock;
    @Mock
    private MemberRepository memberRepositoryMock;
    @Mock
    private OrganizerRepository organizerRepositoryMock;
    @Mock
    private VenueRepository venueRepositoryMock;
    @Mock
    private ParticipantRepository participantRepositoryMock;

    private EventMapper eventMapper ;
    private EventRegistrationDto eventRegistrationDto ;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        eventMapper = new EventMapperImpl();
        eventRegistrationDto = new EventRegistrationDto();
        autoCloseable= MockitoAnnotations.openMocks(this);
        eventServiceTest=new EventServiceImpl(
                eventRepositoryMock,
                memberRepositoryMock,
                organizerRepositoryMock,
                venueRepositoryMock,
                participantRepositoryMock,
                eventMapper);
    }

    @AfterEach
    void afterEach() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllEvents() {
        eventServiceTest.getAllEvents();
        verify(eventRepositoryMock).findAll();
    }

    @Test
    void getEventById() {
        long eventId=1;
        eventServiceTest.getEventById(eventId);
        verify(eventRepositoryMock).findById(eventId);
    }

    @Test
    void createEvent() {
        Member member = Member.builder()
                .id(1L)
                .email("MemberTest@gmail.com")
                .build();
        Organizer organizer = Organizer.builder()
                .id(1L)
                .name("Test Organizer")
                .build();
        Venue venue = Venue.builder()
                .id(1L)
                .name("Test Venue")
                .build();
        Participant participant = Participant.builder()
                .id(1L)
                .name("Test Participant")
                .build();
        EventRegistrationDto eventDto = EventRegistrationDto.builder()
                .name("Test DTO")
                .location("Test Location")
                .member(member)
                .organizer(organizer)
                .venue(venue)
                .participantSet(Set.of(participant))
                .build();

        Event event = eventMapper.EventRegistrationDtoToEvent(eventDto);

        assertThat(event).isNotNull();

        when(memberRepositoryMock.existsById(member.getId())).thenReturn(true);
        when(organizerRepositoryMock.existsById(organizer.getId())).thenReturn(true);
        when(venueRepositoryMock.existsById(venue.getId())).thenReturn(true);
        when(participantRepositoryMock.existsById(any(Long.class))).thenReturn(true);

        eventServiceTest.createEvent(eventDto);

        verify(eventRepositoryMock).save(event);

    }

    @Test
    void createEventWhenMemberNotPresent() {
        Member member = Member.builder()
                .id(1L)
                .email("MemberTest@gmail.com")
                .build();
        Organizer organizer = Organizer.builder()
                .id(1L)
                .name("Test Organizer")
                .build();
        Venue venue = Venue.builder()
                .id(1L)
                .name("Test Venue")
                .build();
        Participant participant = Participant.builder()
                .id(1L)
                .name("Test Participant")
                .build();
        EventRegistrationDto eventDto = EventRegistrationDto.builder()
                .name("Test DTO")
                .location("Test Location")
                .member(member)
                .organizer(organizer)
                .venue(venue)
                .participantSet(Set.of(participant))
                .build();

        Event event = eventMapper.EventRegistrationDtoToEvent(eventDto);

        assertThat(event).isNotNull();

        when(memberRepositoryMock.existsById(member.getId())).thenReturn(false);
        when(organizerRepositoryMock.existsById(organizer.getId())).thenReturn(true);
        when(venueRepositoryMock.existsById(venue.getId())).thenReturn(true);
        when(participantRepositoryMock.existsById(any(Long.class))).thenReturn(true);

        assertThatThrownBy(()->eventServiceTest.createEvent(eventDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Invalid member. Register the member first.");
    }

    @Test
    void createEventWhenOrganizerNotPresent() {
        Member member = Member.builder()
                .id(1L)
                .email("MemberTest@gmail.com")
                .build();
        Organizer organizer = Organizer.builder()
                .id(1L)
                .name("Test Organizer")
                .build();
        Venue venue = Venue.builder()
                .id(1L)
                .name("Test Venue")
                .build();
        Participant participant = Participant.builder()
                .id(1L)
                .name("Test Participant")
                .build();
        EventRegistrationDto eventDto = EventRegistrationDto.builder()
                .name("Test DTO")
                .location("Test Location")
                .member(member)
                .organizer(organizer)
                .venue(venue)
                .participantSet(Set.of(participant))
                .build();

        Event event = eventMapper.EventRegistrationDtoToEvent(eventDto);

        assertThat(event).isNotNull();

        when(memberRepositoryMock.existsById(member.getId())).thenReturn(true);
        when(organizerRepositoryMock.existsById(organizer.getId())).thenReturn(false);
        when(venueRepositoryMock.existsById(venue.getId())).thenReturn(true);
        when(participantRepositoryMock.existsById(any(Long.class))).thenReturn(true);

        assertThatThrownBy(()->eventServiceTest.createEvent(eventDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Invalid organizer. Register the organizer first.");
    }
    @Test
    void createEventWhenVenueNotPresent() {
        Member member = Member.builder()
                .id(1L)
                .email("MemberTest@gmail.com")
                .build();
        Organizer organizer = Organizer.builder()
                .id(1L)
                .name("Test Organizer")
                .build();
        Venue venue = Venue.builder()
                .id(1L)
                .name("Test Venue")
                .build();
        Participant participant = Participant.builder()
                .id(1L)
                .name("Test Participant")
                .build();
        EventRegistrationDto eventDto = EventRegistrationDto.builder()
                .name("Test DTO")
                .location("Test Location")
                .member(member)
                .organizer(organizer)
                .venue(venue)
                .participantSet(Set.of(participant))
                .build();

        Event event = eventMapper.EventRegistrationDtoToEvent(eventDto);

        assertThat(event).isNotNull();

        when(memberRepositoryMock.existsById(member.getId())).thenReturn(true);
        when(organizerRepositoryMock.existsById(organizer.getId())).thenReturn(true);
        when(venueRepositoryMock.existsById(venue.getId())).thenReturn(false);
        when(participantRepositoryMock.existsById(any(Long.class))).thenReturn(true);

        assertThatThrownBy(()->eventServiceTest.createEvent(eventDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Invalid venue. Register the venue first.");
    }
    @Test
    void createEventWhenParticipantNotPresent() {
        Member member = Member.builder()
                .id(1L)
                .email("MemberTest@gmail.com")
                .build();
        Organizer organizer = Organizer.builder()
                .id(1L)
                .name("Test Organizer")
                .build();
        Venue venue = Venue.builder()
                .id(1L)
                .name("Test Venue")
                .build();
        Participant participant = Participant.builder()
                .id(1L)
                .name("Test Participant")
                .build();
        EventRegistrationDto eventDto = EventRegistrationDto.builder()
                .name("Test DTO")
                .location("Test Location")
                .member(member)
                .organizer(organizer)
                .venue(venue)
                .participantSet(Set.of(participant))
                .build();

        Event event = eventMapper.EventRegistrationDtoToEvent(eventDto);

        assertThat(event).isNotNull();

        when(memberRepositoryMock.existsById(member.getId())).thenReturn(true);
        when(organizerRepositoryMock.existsById(organizer.getId())).thenReturn(true);
        when(venueRepositoryMock.existsById(venue.getId())).thenReturn(true);
        when(participantRepositoryMock.existsById(any(Long.class))).thenReturn(false);

        assertThatThrownBy(()->eventServiceTest.createEvent(eventDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Invalid participant. Register the participant first: participantId="+(participant == null ? "null" : participant.getId()));
    }
    @Test
    void updateEvent() {
        Event exitingEvent = Event.builder().id((long)1).build();
        Event updatedEvent = Event.builder() .id(1L) .name("Updated Event").build();

        when(eventRepositoryMock.findById(exitingEvent.getId())).thenReturn(Optional.of(exitingEvent));
        when(eventRepositoryMock.save(updatedEvent)).thenReturn(updatedEvent);

        eventServiceTest.updateEvent(updatedEvent.getId(),updatedEvent);

        verify(eventRepositoryMock).save(updatedEvent);
    }
    @Test
    void updateEventWhenEventNotPresent() {
        Event exitingEvent = Event.builder().id((long)1).build();
        Event updatedEvent = Event.builder() .id(1L) .name("Updated Event").build();

        when(eventRepositoryMock.findById(exitingEvent.getId())).thenReturn(Optional.empty());


        Optional<Event> result = eventServiceTest.updateEvent(updatedEvent.getId(), updatedEvent);

        assertThat(result).isNotPresent();
    }

    @Test
    void deleteEventById() {
        long eventId = 1;

        when(eventRepositoryMock.existsById(eventId)).thenReturn(true);

        eventServiceTest.deleteEventById(eventId);

        verify(eventRepositoryMock).deleteById(eventId);
    }

    @Test
    void deleteEventByIdWhenEventNotPresent() {
        long eventId = 1;

        when(eventRepositoryMock.existsById(eventId)).thenReturn(false);

        boolean result=eventServiceTest.deleteEventById(eventId);

        assertThat(result).isFalse();
    }
}