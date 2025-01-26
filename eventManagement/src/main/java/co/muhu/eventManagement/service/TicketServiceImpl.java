package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.mappers.ticket.TicketMapper;
import co.muhu.eventManagement.model.TicketRegistrationDto;
import co.muhu.eventManagement.repository.EventRepository;
import co.muhu.eventManagement.repository.ParticipantRepository;
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
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final TicketMapper ticketMapper;

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Optional<Ticket> getTicketById(Long id) {

        return ticketRepository.findById(id);
    }

    @Override
    public Ticket createTicket(TicketRegistrationDto ticketRegistrationDto) {
        Event existedEvent = validateTicketEvent(ticketRegistrationDto)
                .orElseThrow(()->new ResourceNotFoundException("There is no event with id :"+ticketRegistrationDto.getEvent().getId()));

        Participant existedParticipant = validateTicketParticipant(ticketRegistrationDto)
                .orElseThrow(()->new ResourceNotFoundException("There is no participant with id :"+ticketRegistrationDto.getParticipant().getId()));

        Ticket ticket = ticketMapper.ticketRegistrationDtoToTicket(ticketRegistrationDto);
        ticket.setEvent(existedEvent);;
        ticket.setParticipant(existedParticipant);

        return ticketRepository.save(ticket);
    }

    private Optional<Event> validateTicketEvent(TicketRegistrationDto ticketRegistrationDto){
        if (ticketRegistrationDto.getEvent()==null || ticketRegistrationDto.getEvent().getId()==null|| !eventRepository.existsById(ticketRegistrationDto.getEvent().getId())){
            return Optional.empty();
        }
        return eventRepository.findById(ticketRegistrationDto.getEvent().getId());
    }
    private Optional<Participant> validateTicketParticipant(TicketRegistrationDto ticketRegistrationDto){
        if (ticketRegistrationDto.getParticipant()==null || ticketRegistrationDto.getParticipant().getId()==null|| !participantRepository.existsById(ticketRegistrationDto.getParticipant().getId())){
            return Optional.empty();
        }
        return participantRepository.findById(ticketRegistrationDto.getParticipant().getId());
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
        if (!eventRepository.existsById(eventId)){
            throw new ResourceNotFoundException("There is no event with id :"+eventId);
        }
        return ticketRepository.findAllByEventId(eventId);
    }

    @Override
    public List<Ticket> getTicketsByParticipantId(Long participantId) {
        if (!participantRepository.existsById(participantId)){
            throw new ResourceNotFoundException("There is no participant with id :"+participantId);
        }
        return ticketRepository.findAllByParticipantId(participantId);
    }
}
