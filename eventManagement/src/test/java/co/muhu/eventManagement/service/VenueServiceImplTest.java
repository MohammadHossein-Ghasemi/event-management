package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Venue;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.repository.EventRepository;
import co.muhu.eventManagement.repository.VenueRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class VenueServiceImplTest {

    private VenueServiceImpl venueServiceTest;

    @Mock
    private VenueRepository venueRepositoryMock;
    @Mock
    private EventRepository eventRepositoryMock;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        venueServiceTest= new VenueServiceImpl(venueRepositoryMock,eventRepositoryMock);
    }

    @AfterEach
    void afterEach() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllVenues() {
        venueServiceTest.getAllVenues();
        verify(venueRepositoryMock).findAll();
    }

    @Test
    void getVenueById() {
        long venueId=1;
        venueServiceTest.getVenueById(venueId);
        verify(venueRepositoryMock).findById(venueId);
    }

    @Test
    void createVenue() {
        Event event = Event.builder().id((long)1).build();
        Venue newVenue= Venue.builder().id((long)1).eventSet(Set.of(event)).build();
        boolean allEventCheck = newVenue.getEventSet().stream()
                .allMatch(event1 -> eventRepositoryMock.existsById(event1.getId()));
        when(allEventCheck).thenReturn(true);
        venueServiceTest.createVenue(newVenue);
        verify(venueRepositoryMock).save(newVenue);
    }
    @Test
    void createVenueWhenEventNotPresent() {
        Event event = Event.builder().id((long)1).build();
        Venue newVenue= Venue.builder().id((long)1).eventSet(Set.of(event)).build();
        boolean allEventCheck = newVenue.getEventSet().stream()
                .allMatch(event1 -> eventRepositoryMock.existsById(event1.getId()));
        when(allEventCheck).thenReturn(false);
        assertThatThrownBy(()->venueServiceTest.createVenue(newVenue))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("One or more associated events do not exist.");
    }

    @Test
    void updateVenue() {
        Venue exitingVenue= Venue.builder().id((long)1).build();
        Venue updateVenue= Venue.builder().id(exitingVenue.getId()).name("Update Venue").build();

        when(venueRepositoryMock.findById(exitingVenue.getId())).thenReturn(Optional.of(exitingVenue));
        when(venueRepositoryMock.save(any(Venue.class))).thenReturn(updateVenue);

        Optional<Venue> result = venueServiceTest.updateVenue(exitingVenue.getId(), updateVenue);

        assertThat(result).isPresent();
        assertThat(result).hasValueSatisfying(updatedVenue->
                assertThat(updatedVenue.getName()).isEqualTo(updateVenue.getName()));

        verify(venueRepositoryMock).findById(exitingVenue.getId());
        verify(venueRepositoryMock).save(any(Venue.class));
    }

    @Test
    void updateVenueWhenVenueNotPresent() {
        Venue exitingVenue= Venue.builder().id((long)1).build();
        Venue updateVenue= Venue.builder().id(exitingVenue.getId()).name("Update Venue").build();

        when(venueRepositoryMock.findById(exitingVenue.getId())).thenReturn(Optional.empty());

        Optional<Venue> result = venueServiceTest.updateVenue(exitingVenue.getId(), updateVenue);

        assertThat(result).isNotPresent();

        verify(venueRepositoryMock).findById(exitingVenue.getId());
    }

    @Test
    void deleteVenueById() {
        long venueId=1;

        when(venueRepositoryMock.existsById(venueId)).thenReturn(true);

        venueServiceTest.deleteVenueById(venueId);

        verify(venueRepositoryMock).deleteById(venueId);
    }

    @Test
    void deleteVenueByIdWhenVenueNotPresent() {
        long venueId=1;

        when(venueRepositoryMock.existsById(venueId)).thenReturn(false);

        boolean result = venueServiceTest.deleteVenueById(venueId);

        assertThat(result).isFalse();
    }
}