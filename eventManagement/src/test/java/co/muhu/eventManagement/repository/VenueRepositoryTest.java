package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Venue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VenueRepositoryTest {
    @Autowired
    VenueRepository venueRepositoryTest;
    @Autowired
    EventRepository eventRepositoryTest;
    @Test
    void existsById() {
        Event event = Event.builder()
                .name("Test Event")
                .description("Happy Test Event")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(5))
                .build();
        event=eventRepositoryTest.save(event);
        assertThat(event).isNotNull();

        Venue venue = Venue.builder()
                .name("Test Venue")
                .address("Test Address")
                .eventSet(Set.of(event))
                .build();
        venue=venueRepositoryTest.save(venue);
        assertThat(venue).isNotNull();

        boolean result = venueRepositoryTest.existsById(venue.getId());

        assertThat(result).isTrue();
    }
}