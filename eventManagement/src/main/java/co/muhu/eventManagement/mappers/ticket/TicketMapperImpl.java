package co.muhu.eventManagement.mappers.ticket;

import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.model.TicketRegistrationDto;
import org.springframework.stereotype.Component;

@Component
public class TicketMapperImpl implements TicketMapper {
    @Override
    public Ticket ticketRegistrationDtoToTicket(TicketRegistrationDto ticketRegistrationDto) {
        return Ticket.builder()
                .price(ticketRegistrationDto.getPrice())
                .status(ticketRegistrationDto.getStatus())
                .event(ticketRegistrationDto.getEvent())
                .participant(ticketRegistrationDto.getParticipant())
                .build();
    }
}
