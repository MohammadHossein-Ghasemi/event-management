package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Organizer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrganizerRepositoryTest {
    @Autowired
    private OrganizerRepository organizerRepositoryTest;
    @Autowired
    private EventRepository eventRepositoryTest;

    @Test
    void existsById() {
        Organizer newOrganizer = Organizer.builder().name("Organizer Test").build();

        Organizer savedOrganizer = organizerRepositoryTest.save(newOrganizer);

        assertThat(savedOrganizer).isNotNull();

        boolean result = organizerRepositoryTest.existsById(savedOrganizer.getId());

        assertThat(result).isTrue();
    }

    @Test
    void findByEventId() {
        Organizer organizer = Organizer.builder()
                .name("Test Organizer")
                .contactInfo("contact@example.com")
                .build();
        organizer = organizerRepositoryTest.save(organizer);

        assertThat(organizer).isNotNull();

        Event event = Event.builder()
                .name("New Year")
                .description("Happy New Year")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(5))
                .organizer(organizer)
                .build();
        event = eventRepositoryTest.save(event);
        
        assertThat(event).isNotNull();

        List<Organizer> result = organizerRepositoryTest.findByEventId(event.getId());

        assertThat(result).isNotEmpty();
    }
}