package co.muhu.eventManagement.mappers.ticket;

import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.model.TicketDto;
import co.muhu.eventManagement.model.TicketRegistrationDto;
import org.springframework.stereotype.Component;

public class TicketMapper {
    public static Ticket ticketRegistrationDtoToTicket(TicketRegistrationDto ticketRegistrationDto) {
        return Ticket.builder()
                .price(ticketRegistrationDto.getPrice())
                .status(ticketRegistrationDto.getStatus())
                .event(ticketRegistrationDto.getEvent())
                .participant(ticketRegistrationDto.getParticipant())
                .build();
    }

    public static TicketDto ticketToTicketDto(Ticket ticket){
        return TicketDto.builder()
                .id(ticket.getId())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .eventId(ticket.getEvent().getId())
                .participantId(ticket.getParticipant().getId())
                .purchaseDate(ticket.getPurchaseDate())
                .build();
    }
}
