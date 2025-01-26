package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.mappers.ticket.TicketMapper;
import co.muhu.eventManagement.model.TicketDto;
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

    @Override
    public List<TicketDto> getAllTickets() {

        return ticketRepository.findAll()
                .stream()
                .map(TicketMapper::ticketToTicketDto)
                .toList();
    }

    @Override
    public Optional<TicketDto> getTicketById(Long id) {

        return ticketRepository.findById(id).map(TicketMapper::ticketToTicketDto);
    }

    @Override
    public TicketDto createTicket(TicketRegistrationDto ticketRegistrationDto) {
        Event existedEvent = validateTicketEvent(ticketRegistrationDto)
                .orElseThrow(()->new ResourceNotFoundException("There is no event with id :"+ticketRegistrationDto.getEvent().getId()));

        Participant existedParticipant = validateTicketParticipant(ticketRegistrationDto)
                .orElseThrow(()->new ResourceNotFoundException("There is no participant with id :"+ticketRegistrationDto.getParticipant().getId()));

        Ticket ticket = TicketMapper.ticketRegistrationDtoToTicket(ticketRegistrationDto);
        ticket.setEvent(existedEvent);;
        ticket.setParticipant(existedParticipant);

        return TicketMapper.ticketToTicketDto(ticketRepository.save(ticket));
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
    public Optional<TicketDto> updateTicket(Long id, Ticket ticket) {
        AtomicReference<Optional<TicketDto>> foundedTicket = new AtomicReference<>();

        ticketRepository.findById(id).ifPresentOrElse(
                updateTicket->{
                    updateTicket.setEvent(ticket.getEvent());
                    updateTicket.setStatus(ticket.getStatus());
                    updateTicket.setPrice(ticket.getPrice());
                    updateTicket.setParticipant(ticket.getParticipant());
                    updateTicket.setPurchaseDate(ticket.getPurchaseDate());
                    foundedTicket.set(Optional.of(TicketMapper.ticketToTicketDto(ticketRepository.save(updateTicket))));
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
    public List<TicketDto> getTicketsByEventId(Long eventId) {
        if (!eventRepository.existsById(eventId)){
            throw new ResourceNotFoundException("There is no event with id :"+eventId);
        }
        return ticketRepository.findAllByEventId(eventId)
                .stream()
                .map(TicketMapper::ticketToTicketDto)
                .toList();
    }

    @Override
    public List<TicketDto> getTicketsByParticipantId(Long participantId) {
        if (!participantRepository.existsById(participantId)){
            throw new ResourceNotFoundException("There is no participant with id :"+participantId);
        }
        return ticketRepository.findAllByParticipantId(participantId)
                .stream()
                .map(TicketMapper::ticketToTicketDto)
                .toList();
    }
}
