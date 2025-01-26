package co.muhu.eventManagement.mappers.ticket;

import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.model.TicketRegistrationDto;

public interface TicketMapper {
    Ticket ticketRegistrationDtoToTicket(TicketRegistrationDto ticketRegistrationDto);
}
