package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryTest {
    @Autowired
    EventRepository eventRepositoryTest;

    @Test
    void existsById() {
        Event newEvent = Event.builder()
                .name("New Year")
                .description("Happy New Year")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(5))
                .build();
        Event savedEvent=eventRepositoryTest.save(newEvent);

        boolean actual = eventRepositoryTest.existsById(savedEvent.getId());

        assertThat(actual).isTrue();

    }
    @Test
    void existsByIdWhenIdNotPresent(){
        Long eventId= (long) -1;
        boolean actual = eventRepositoryTest.existsById(eventId);
        assertThat(actual).isFalse();
    }
}