package co.muhu.eventManagement.mappers.ticket;

import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.mappers.event.EventMapper;
import co.muhu.eventManagement.mappers.participant.ParticipantMapper;
import co.muhu.eventManagement.model.EventDto;
import co.muhu.eventManagement.model.ParticipantDto;
import co.muhu.eventManagement.model.TicketDto;
import co.muhu.eventManagement.model.TicketRegistrationDto;
import org.springframework.stereotype.Component;

public class TicketMapper {
    public static Ticket ticketRegistrationDtoToTicket(TicketRegistrationDto ticketRegistrationDto) {
        if (ticketRegistrationDto==null){
            return new Ticket();
        }
        return Ticket.builder()
                .price(ticketRegistrationDto.getPrice())
                .status(ticketRegistrationDto.getStatus())
                .event(ticketRegistrationDto.getEvent())
                .participant(ticketRegistrationDto.getParticipant())
                .build();
    }

    public static TicketDto ticketToTicketDto(Ticket ticket){
        if (ticket==null){
            return new TicketDto();
        }
        EventDto eventDto = EventMapper.eventToEventDto(ticket.getEvent());
        ParticipantDto participantDto = ParticipantMapper.participantToParticipantDto(ticket.getParticipant());
        return TicketDto.builder()
                .id(ticket.getId())
                .price(ticket.getPrice())
                .status(ticket.getStatus())
                .eventDto(eventDto)
                .participantDto(participantDto)
                .purchaseDate(ticket.getPurchaseDate())
                .build();
    }
}
