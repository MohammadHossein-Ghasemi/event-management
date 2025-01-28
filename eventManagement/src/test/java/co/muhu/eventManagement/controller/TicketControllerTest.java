package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.entity.Ticket;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.TicketDto;
import co.muhu.eventManagement.model.TicketRegistrationDto;
import co.muhu.eventManagement.service.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @MockitoBean
    TicketService ticketService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllTickets() throws Exception {
        given(ticketService.getAllTickets()).willReturn(List.of());

        mockMvc.perform(get(TicketController.TICKET_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));
    }

    @Test
    void getTicketById() throws Exception {
        TicketDto ticketDto = TicketDto.builder()
                .id(1L)
                .status("Valid")
                .build();

        given(ticketService.getTicketById(ticketDto.getId())).willReturn(Optional.of(ticketDto));

        mockMvc.perform(get(TicketController.TICKET_PATH_ID,ticketDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(ticketDto.getId().intValue())))
                .andExpect(jsonPath("$.status",is(ticketDto.getStatus())));
    }

    @Test
    void getTicketByIdWhenTicketNotFound() throws Exception {
        TicketDto ticketDto = TicketDto.builder()
                .id(1L)
                .status("Valid")
                .build();

        given(ticketService.getTicketById(ticketDto.getId())).willReturn(Optional.empty());

        mockMvc.perform(get(TicketController.TICKET_PATH_ID,ticketDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void saveTicket() throws Exception {
        TicketRegistrationDto ticketDto = TicketRegistrationDto.builder()
                .status("Valid")
                .participant(new Participant())
                .event(new Event())
                .build();

        given(ticketService.createTicket(ticketDto)).willReturn(any(TicketDto.class));

        mockMvc.perform(post(TicketController.TICKET_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateTicket() throws Exception {
        TicketDto ticketDto = TicketDto.builder()
                .id(1L)
                .status("Valid")
                .build();

        given(ticketService.updateTicket(any(Long.class),any(Ticket.class))).willReturn(Optional.of(ticketDto));

        mockMvc.perform(put(TicketController.TICKET_PATH_ID,ticketDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Ticket())))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(ticketDto.getId().intValue())))
                .andExpect(jsonPath("$.status",is(ticketDto.getStatus())));

    }

    @Test
    void updateTicketWhenTicketNotFound() throws Exception {
        TicketDto ticketDto = TicketDto.builder()
                .id(1L)
                .status("Valid")
                .build();

        given(ticketService.updateTicket(any(Long.class),any(Ticket.class))).willReturn(Optional.empty());

        mockMvc.perform(put(TicketController.TICKET_PATH_ID,ticketDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Ticket())))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deletedTicket() throws Exception {

        given(ticketService.deleteTicketById(any(Long.class))).willReturn(true);

        mockMvc.perform(delete(TicketController.TICKET_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deletedTicketWhenTicketNotFound() throws Exception {

        given(ticketService.deleteTicketById(any(Long.class))).willReturn(false);

        mockMvc.perform(delete(TicketController.TICKET_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }


    @Test
    void getALLTicketsByEventId() throws Exception {

        given(ticketService.getTicketsByEventId(any(Long.class))).willReturn(List.of());

        mockMvc.perform(get(TicketController.TICKET_PATH+"/event/{eventId}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));
    }

    @Test
    void getALLTicketsByEventIdWhenEventNotFound() throws Exception {

        given(ticketService.getTicketsByEventId(any(Long.class))).willThrow(new ResourceNotFoundException("There is no event with id : 1"));

        mockMvc.perform(get(TicketController.TICKET_PATH+"/event/{eventId}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getAllTicketsByParticipantId() throws Exception {

        given(ticketService.getTicketsByParticipantId(any(Long.class))).willReturn(List.of());

        mockMvc.perform(get(TicketController.TICKET_PATH+"/participant/{participantId}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));
    }

    @Test
    void getAllTicketsByParticipantIdWhenParticipantNotFound() throws Exception {

        given(ticketService.getTicketsByParticipantId(any(Long.class))).willThrow(new ResourceNotFoundException("There is no participant with id : 1"));

        mockMvc.perform(get(TicketController.TICKET_PATH+"/participant/{participantId}",1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }
}