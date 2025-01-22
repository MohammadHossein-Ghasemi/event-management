package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> getTicketById(Long id) {

        return ticketRepository.findById(id);
    }

    @Override
    public Ticket createTicket(Ticket ticket) {

        return ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> updateTicket(Long id, Ticket ticket) {
        AtomicReference<Optional<Ticket>> foundedTicket = new AtomicReference<>();

        ticketRepository.findById(id).ifPresentOrElse(
                updateTicket->{
                    updateTicket.setEvent(ticket.getEvent());
                    updateTicket.setStatus(ticket.getStatus());
                    updateTicket.setPrice(ticket.getPrice());
                    updateTicket.setParticipant(ticket.getParticipant());
                    updateTicket.setPurchaseDate(ticket.getPurchaseDate());
                    foundedTicket.set(Optional.of(ticketRepository.save(updateTicket)));
                },
                ()-> foundedTicket.set(Optional.empty())
        );
        return foundedTicket.get();
    }

    @Override
    public boolean deleteTicketById(Long id) {
        if (ticketRepository.existsById(id)){
            ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Ticket> getTicketsByEventId(Long eventId) {
        return ticketRepository.findAllByEventId(eventId);
    }

    @Override
    public List<Ticket> getTicketsByParticipantId(Long participantId) {
        return ticketRepository.findAllByParticipantId(participantId);
    }
}
