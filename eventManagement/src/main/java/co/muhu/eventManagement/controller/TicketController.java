package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.TicketRegistrationDto;
import co.muhu.eventManagement.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketController {
    public static final String TICKET_PATH="/api/v1/ticket";
    public static final String TICKET_PATH_ID="/api/v1/ticket/{ticketId}";
    private final TicketService ticketService;

    @GetMapping(value = TICKET_PATH)
    public ResponseEntity<List<Ticket>> getAllTickets(){
        List<Ticket> ticketList = ticketService.getAllTickets();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,TICKET_PATH)
                .body(ticketList);
    }

    @GetMapping(value = TICKET_PATH_ID)
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long ticketId){
        Ticket foundedTicket = ticketService.getTicketById(ticketId)
                .orElseThrow(()-> new ResourceNotFoundException("There is no thicket with this id : "+ticketId));
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,TICKET_PATH+"/"+ticketId)
                .body(foundedTicket);
    }

    @PostMapping(value = TICKET_PATH)
    public ResponseEntity<Ticket> saveTicket(@Validated @RequestBody TicketRegistrationDto ticketRegistrationDto){
        Ticket savedTicket = ticketService.createTicket(ticketRegistrationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION,TICKET_PATH)
                .body(savedTicket);
    }

    @PutMapping(value = TICKET_PATH_ID)
    public ResponseEntity<Ticket> updateTicket(@Validated @RequestBody Ticket ticket,
                                               @PathVariable Long ticketId){
        Ticket updatedTicket = ticketService.updateTicket(ticketId, ticket)
                .orElseThrow(() -> new ResourceNotFoundException("There is no ticket with this id : " + ticketId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,TICKET_PATH+"/"+ticketId)
                .body(updatedTicket);
    }

    @DeleteMapping(value = TICKET_PATH_ID)
    public ResponseEntity<?> deletedTicket(@PathVariable Long ticketId){
        boolean isDeleted = ticketService.deleteTicketById(ticketId);
        if(!isDeleted){
            throw new ResourceNotFoundException("There is no ticket with this id : " + ticketId);
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .header(HttpHeaders.LOCATION,TICKET_PATH+"/"+ticketId)
                .build();
    }

    @GetMapping(value = TICKET_PATH+"/event/{eventId}")
    public ResponseEntity<List<Ticket>> getALLTicketsByEventId(@PathVariable Long eventId){
        List<Ticket> ticketList = ticketService.getTicketsByEventId(eventId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,TICKET_PATH+"/event/"+eventId)
                .body(ticketList);
    }

    @GetMapping(value = TICKET_PATH+"/participant/{participantId}")
    public ResponseEntity<List<Ticket>> getAllTicketsByParticipantId(@PathVariable Long participantId){
        List<Ticket> ticketList = ticketService.getTicketsByParticipantId(participantId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.LOCATION,TICKET_PATH+"/participant/"+participantId)
                .body(ticketList);
    }











}
