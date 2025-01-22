package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.repository.EventRepository;
import co.muhu.eventManagement.repository.OrganizerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class OrganizerServiceImplTest {
    private OrganizerServiceImpl organizerServiceTest;

    @Mock
    private OrganizerRepository organizerRepositoryMock;

    @Mock
    private EventRepository eventRepositoryMock;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        organizerServiceTest=new OrganizerServiceImpl(organizerRepositoryMock,eventRepositoryMock);
    }

    @AfterEach
    void afterEach() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllOrganizers() {
        organizerServiceTest.getAllOrganizers();

        verify(organizerRepositoryMock).findAll();
    }

    @Test
    void getOrganizerById() {
        long organizerId=1;
        organizerServiceTest.getOrganizerById(organizerId);

        verify(organizerRepositoryMock).findById(organizerId);
    }

    @Test
    void createOrganizer() {
        Organizer newOrganizer= Organizer.builder().id((long)1).build();

        organizerServiceTest.createOrganizer(newOrganizer);

        verify(organizerRepositoryMock).save(any(Organizer.class));
    }

    @Test
    void updateOrganizer() {
        Organizer exitingOrganizer=Organizer.builder().id((long)1).build();
        Organizer updateOrganizer=Organizer.builder().id((long)1).name("Updated Organizer").build();

        when(organizerRepositoryMock.findById(exitingOrganizer.getId())).thenReturn(Optional.of(exitingOrganizer));
        when(organizerRepositoryMock.save(any(Organizer.class))).thenReturn(updateOrganizer);

        Optional<Organizer> result = organizerServiceTest.updateOrganizer(exitingOrganizer.getId(), updateOrganizer);

        assertThat(result).isPresent();
        assertThat(result).hasValueSatisfying(organizer ->
                assertThat(organizer.getName()).isEqualTo(updateOrganizer.getName())
        );

        verify(organizerRepositoryMock).findById(exitingOrganizer.getId());
        verify(organizerRepositoryMock).save(any(Organizer.class));
    }
    @Test
    void updateOrganizerWhenOrganizerNotPresent() {
        Organizer exitingOrganizer=Organizer.builder().id((long)1).build();
        Organizer updateOrganizer=Organizer.builder().id((long)1).name("Updated Organizer").build();

        when(organizerRepositoryMock.findById(exitingOrganizer.getId())).thenReturn(Optional.empty());

        Optional<Organizer> result = organizerServiceTest.updateOrganizer(exitingOrganizer.getId(), updateOrganizer);

        assertThat(result).isNotPresent();

        verify(organizerRepositoryMock).findById(exitingOrganizer.getId());
    }

    @Test
    void deleteOrganizerById() {
        long organizerId = 1;

        when(organizerRepositoryMock.existsById(organizerId)).thenReturn(true);

        boolean result = organizerServiceTest.deleteOrganizerById(organizerId);

        assertThat(result).isTrue();

        verify(organizerRepositoryMock).deleteById(organizerId);
    }
    @Test
    void deleteOrganizerByIdWhenOrganizerNotPresent() {
        long organizerId = 1;

        when(organizerRepositoryMock.existsById(organizerId)).thenReturn(false);

        boolean result = organizerServiceTest.deleteOrganizerById(organizerId);

        assertThat(result).isFalse();
    }

    @Test
    void getOrganizerByEventId() {
        long eventId=1;
        when(eventRepositoryMock.existsById(eventId)).thenReturn(true);
        organizerServiceTest.getOrganizerByEventId(eventId);

        verify(organizerRepositoryMock).findByEventId(eventId);
    }
    @Test
    void getOrganizerByEventIdWhenEventNotPresent() {
        long eventId=1;
        when(eventRepositoryMock.existsById(eventId)).thenReturn(false);

        assertThatThrownBy(()->organizerServiceTest.getOrganizerByEventId(eventId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("There is no event with this id : "+eventId);
    }
}