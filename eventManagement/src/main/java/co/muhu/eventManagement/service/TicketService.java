package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.model.TicketDto;
import co.muhu.eventManagement.model.TicketRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    List<TicketDto> getAllTickets();
    Optional<TicketDto> getTicketById(Long id);
    TicketDto createTicket(TicketRegistrationDto ticketRegistrationDto);
    Optional<TicketDto> updateTicket(Long id, Ticket ticket);
    boolean deleteTicketById(Long id);
    List<TicketDto> getTicketsByEventId(Long eventId);
    List<TicketDto> getTicketsByParticipantId(Long participantId);
}
