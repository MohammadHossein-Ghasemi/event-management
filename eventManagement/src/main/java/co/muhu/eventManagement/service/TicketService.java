package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    List<Ticket> getAllTickets();
    Optional<Ticket> getTicketById(Long id);
    Ticket createTicket(Ticket ticket);
    Optional<Ticket> updateTicket(Long id, Ticket ticket);
    boolean deleteTicketById(Long id);
    List<Ticket> getTicketsByEventId(Long eventId);
    List<Ticket> getTicketsByParticipantId(Long participantId);
}
