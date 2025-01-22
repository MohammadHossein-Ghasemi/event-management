package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParticipantRepositoryTest {

    @Autowired
    ParticipantRepository participantRepositoryTest;

    @Autowired
    EventRepository eventRepositoryTest;

    @Test
    void existsById() {
        Participant newParticipant = Participant.builder()
                .name("Test")
                .email("test@gmail.com")
                .build();
        Participant savedParticipant = participantRepositoryTest.save(newParticipant);

        assertThat(savedParticipant).isNotNull();

        boolean result = participantRepositoryTest.existsById(savedParticipant.getId());

        assertThat(result).isTrue();
    }

    @Test
    void findAllByEventSetId() {

        Event newEvent = Event.builder()
                .name("New Year")
                .description("Happy New Year")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(5))
                .build();
        Event savedEvent=eventRepositoryTest.save(newEvent);
        boolean existsById = eventRepositoryTest.existsById(savedEvent.getId());

        assertThat(savedEvent).isNotNull();
        assertThat(existsById).isTrue();

        Participant newParticipant = Participant.builder()
                .name("Test")
                .email("test@gmail.com")
                .eventSet(Set.of(savedEvent))
                .build();
        Participant savedParticipant = participantRepositoryTest.save(newParticipant);
        assertThat(savedParticipant).isNotNull();

        List<Participant> result = participantRepositoryTest.findAllByEventSetId(savedEvent.getId());

        assertThat(result).isNotEmpty();
    }

}