package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.entity.Participant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedBackRepositoryTest {

    @Autowired
    FeedBackRepository feedBackRepositoryTest;
    @Autowired
    EventRepository eventRepositoryTest;
    @Autowired
    ParticipantRepository participantRepositoryTest;
    @Test
    void existsById() {
        Event existedEvent = Event.builder()
                .name("Test Event.")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(5))
                .build();
        existedEvent=eventRepositoryTest.save(existedEvent);
        Participant existedParticipant = Participant.builder()
                .name("Participant Test")
                .email("participant@example.com")
                .build();
        existedParticipant = participantRepositoryTest.save(existedParticipant);

        assertThat(existedEvent).isNotNull();
        assertThat(existedParticipant).isNotNull();

        FeedBack newFeedBack= FeedBack.builder()
                .comments("Feedback Test")
                .rating(10)
                .event(existedEvent)
                .participant(existedParticipant)
                .submittedDate(LocalDateTime.now())
                .build();
        FeedBack savedFeedBack = feedBackRepositoryTest.save(newFeedBack);

        boolean result = feedBackRepositoryTest.existsById(newFeedBack.getId());

        assertThat(result).isTrue();
        assertThat(savedFeedBack).isEqualTo(newFeedBack);
    }

    @Test
    void findAllByEventId() {
        Event existedEvent = Event.builder()
                .name("Test Event.")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(5))
                .build();
        existedEvent=eventRepositoryTest.save(existedEvent);

        Participant existedParticipant = Participant.builder()
                .name("Participant Test")
                .email("participant@example.com")
                .build();
        existedParticipant = participantRepositoryTest.save(existedParticipant);

        assertThat(existedEvent).isNotNull();
        assertThat(existedParticipant).isNotNull();

        FeedBack newFeedBack= FeedBack.builder()
                .comments("Feedback Test")
                .rating(10)
                .event(existedEvent)
                .participant(existedParticipant)
                .submittedDate(LocalDateTime.now())
                .build();
        feedBackRepositoryTest.save(newFeedBack);

        List<FeedBack> result = feedBackRepositoryTest.findAllByEventId(existedEvent.getId());

        assertThat(result).isNotEmpty();
    }

    @Test
    void findAllByParticipantId() {
    }
}