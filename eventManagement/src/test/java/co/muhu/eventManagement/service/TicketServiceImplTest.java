package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.TicketDto;
import co.muhu.eventManagement.model.TicketRegistrationDto;
import co.muhu.eventManagement.repository.EventRepository;
import co.muhu.eventManagement.repository.ParticipantRepository;
import co.muhu.eventManagement.repository.TicketRepository;
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

class TicketServiceImplTest {
    private TicketServiceImpl ticketServiceTest;

    @Mock
    private TicketRepository ticketRepositoryMock;
    @Mock
    private EventRepository eventRepositoryMock;
    @Mock
    private ParticipantRepository participantRepositoryMock;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        ticketServiceTest = new TicketServiceImpl(ticketRepositoryMock, eventRepositoryMock, participantRepositoryMock);
    }

    @AfterEach
    void afterEach() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllTickets() {
        ticketServiceTest.getAllTickets();
        verify(ticketRepositoryMock).findAll();
    }

    @Test
    void getTicketById() {
        long ticketId=1;
        ticketServiceTest.getTicketById(ticketId);
        verify(ticketRepositoryMock).findById(ticketId);
    }

    @Test
    void createTicket() {
        Event event = Event.builder()
                .id(10L)
                .participantSet(Set.of())
                .build();
        Participant participant = Participant.builder()
                .id(1L)
                .build();
        TicketRegistrationDto newTicket = TicketRegistrationDto.builder()
                .event(event)
                .participant(participant)
                .build();
        when(eventRepositoryMock.existsById(event.getId())).thenReturn(true);
        when(eventRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(event));
        when(participantRepositoryMock.existsById(participant.getId())).thenReturn(true);
        when(participantRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(participant));
        ticketServiceTest.createTicket(newTicket);
        verify(ticketRepositoryMock).save(any(Ticket.class));
    }
    @Test
    void createTicketWhenEventNotPresent() {
        Event event = Event.builder().id((long)10).build();
        TicketRegistrationDto newTicket = TicketRegistrationDto.builder().event(event).build();
        when(eventRepositoryMock.existsById(event.getId())).thenReturn(false);

        assertThatThrownBy(()->ticketServiceTest.createTicket(newTicket))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("There is no event with id :"+event.getId());
    }

    @Test
    void updateTicket() {
        Ticket exitingTicket = Ticket.builder().id((long)1).build();
        Ticket updateTicket = Ticket.builder().id((long)1).status("Expired").build();

        when(ticketRepositoryMock.findById(exitingTicket.getId())).thenReturn(Optional.of(exitingTicket));
        when(ticketRepositoryMock.save(any(Ticket.class))).thenReturn(updateTicket);

        Optional<TicketDto> result = ticketServiceTest.updateTicket(exitingTicket.getId(), updateTicket);

        assertThat(result).isPresent();
        assertThat(result).hasValueSatisfying(
                updatedTicket-> assertThat(updatedTicket.getStatus()).isEqualTo(updateTicket.getStatus())
        );

        verify(ticketRepositoryMock).findById(exitingTicket.getId());
        verify(ticketRepositoryMock).save(any(Ticket.class));
    }
    @Test
    void updateTicketWhenTicketNotPresent() {
        Ticket exitingTicket = Ticket.builder().id((long)1).build();
        Ticket updateTicket = Ticket.builder().id((long)1).status("Expired").build();

        when(ticketRepositoryMock.findById(exitingTicket.getId())).thenReturn(Optional.empty());

        Optional<TicketDto> result = ticketServiceTest.updateTicket(exitingTicket.getId(), updateTicket);

        assertThat(result).isNotPresent();

        verify(ticketRepositoryMock).findById(exitingTicket.getId());
    }

    @Test
    void deleteTicketById() {
        long ticketId=1;

        when(ticketRepositoryMock.existsById(ticketId)).thenReturn(true);

        ticketServiceTest.deleteTicketById(ticketId);

        verify(ticketRepositoryMock).deleteById(ticketId);
    }
    @Test
    void deleteTicketByIdWhenTicketNotPresent() {
        long ticketId=1;

        when(ticketRepositoryMock.existsById(ticketId)).thenReturn(false);

        boolean result = ticketServiceTest.deleteTicketById(ticketId);

        assertThat(result).isFalse();
    }


    @Test
    void getTicketsByEventId() {
        long eventId=1;

        when(eventRepositoryMock.existsById(eventId)).thenReturn(true);

        ticketServiceTest.getTicketsByEventId(eventId);

        verify(ticketRepositoryMock).findAllByEventId(eventId);
    }
    @Test
    void getTicketsByEventIdWhenEventNotPresent() {
        long eventId=1;

        when(eventRepositoryMock.existsById(eventId)).thenReturn(false);

        assertThatThrownBy(()->ticketServiceTest.getTicketsByEventId(eventId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("There is no event with id :"+eventId);
    }

    @Test
    void getTicketsByParticipantId() {
        long participantId=1;

        when(participantRepositoryMock.existsById(participantId)).thenReturn(true);

        ticketServiceTest.getTicketsByParticipantId(participantId);

        verify(ticketRepositoryMock).findAllByParticipantId(participantId);
    }
    @Test
    void getTicketsByParticipantIdWhenParticipantNotPresent() {
        long participantId=1;

        when(participantRepositoryMock.existsById(participantId)).thenReturn(false);

        assertThatThrownBy(()->ticketServiceTest.getTicketsByParticipantId(participantId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("There is no participant with id :"+participantId);
    }
}