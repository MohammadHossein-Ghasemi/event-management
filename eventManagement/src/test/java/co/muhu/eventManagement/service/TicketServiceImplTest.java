package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.repository.TicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class TicketServiceImplTest {
    private TicketServiceImpl ticketServiceTest;

    @Mock
    private TicketRepository ticketRepositoryMock;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        ticketServiceTest = new TicketServiceImpl(ticketRepositoryMock);
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
        Ticket newTicket = Ticket.builder().id((long)1).build();
        ticketServiceTest.createTicket(newTicket);
        verify(ticketRepositoryMock).save(newTicket);
    }

    @Test
    void updateTicket() {
        Ticket exitingTicket = Ticket.builder().id((long)1).build();
        Ticket updateTicket = Ticket.builder().id((long)1).status("Expired").build();

        when(ticketRepositoryMock.findById(exitingTicket.getId())).thenReturn(Optional.of(exitingTicket));
        when(ticketRepositoryMock.save(any(Ticket.class))).thenReturn(updateTicket);

        Optional<Ticket> result = ticketServiceTest.updateTicket(exitingTicket.getId(), updateTicket);

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

        Optional<Ticket> result = ticketServiceTest.updateTicket(exitingTicket.getId(), updateTicket);

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

        ticketServiceTest.getTicketsByEventId(eventId);

        verify(ticketRepositoryMock).findAllByEventId(eventId);
    }

    @Test
    void getTicketsByParticipantId() {
        long participantId=1;

        ticketServiceTest.getTicketsByParticipantId(participantId);

        verify(ticketRepositoryMock).findAllByParticipantId(participantId);
    }
}