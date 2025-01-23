package co.muhu.eventManagement.repository;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TicketRepositoryTest {

    @Autowired
    TicketRepository ticketRepositoryTest;

    @Autowired
    EventRepository eventRepositoryTest;

    @Autowired
    ParticipantRepository participantRepositoryTest;

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

        Ticket ticket = Ticket.builder()
                .event(event)
                .price(BigDecimal.valueOf(2266.55))
                .purchaseDate(LocalDateTime.now())
                .build();
        ticket=ticketRepositoryTest.save(ticket);
        assertThat(ticket).isNotNull();

        boolean result = ticketRepositoryTest.existsById(ticket.getId());

        assertThat(result).isTrue();
    }

    @Test
    void findAllByEventId() {
        Event event = Event.builder()
                .name("Test Event")
                .description("Happy Test Event")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(5))
                .build();
        event=eventRepositoryTest.save(event);
        assertThat(event).isNotNull();

        Ticket ticket = Ticket.builder()
                .event(event)
                .price(BigDecimal.valueOf(2266.55))
                .purchaseDate(LocalDateTime.now())
                .build();
        ticket=ticketRepositoryTest.save(ticket);
        assertThat(ticket).isNotNull();

        List<Ticket> result = ticketRepositoryTest.findAllByEventId(ticket.getEvent().getId());

        assertThat(result).isNotEmpty();
    }

    @Test
    void findAllByParticipantId() {
        Event event = Event.builder()
                .name("Test Event")
                .description("Happy Test Event")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(5))
                .build();
        event=eventRepositoryTest.save(event);
        assertThat(event).isNotNull();

        Participant participant = Participant.builder()
                .name("Test")
                .email("test@gmail.com")
                .build();
        participant = participantRepositoryTest.save(participant);
        assertThat(participant).isNotNull();

        Ticket ticket = Ticket.builder()
                .event(event)
                .participant(participant)
                .price(BigDecimal.valueOf(2266.55))
                .purchaseDate(LocalDateTime.now())
                .build();
        ticket=ticketRepositoryTest.save(ticket);
        assertThat(ticket).isNotNull();

        List<Ticket> result = ticketRepositoryTest.findAllByParticipantId(ticket.getParticipant().getId());

        assertThat(result).isNotEmpty();
    }
}