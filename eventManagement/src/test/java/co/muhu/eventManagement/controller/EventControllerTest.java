package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.model.EventDto;
import co.muhu.eventManagement.model.EventRegistrationDto;
import co.muhu.eventManagement.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @MockitoBean
    EventService eventService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllEvents() throws Exception {

        given(eventService.getAllEvents()).willReturn(List.of());

        mockMvc.perform(get(EventController.EVENT_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));
    }

    @Test
    void getEventById() throws Exception {

        EventDto eventDto = EventDto.builder()
                .id(1L)
                .name("Test")
                .build();

        given(eventService.getEventById(any(Long.class))).willReturn(Optional.of(eventDto));

        mockMvc.perform(get(EventController.EVENT_PATH_ID,eventDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(eventDto.getId().intValue())))
                .andExpect(jsonPath("$.name",is(eventDto.getName())));
    }

    @Test
    void getEventByIdWhenEventNotFound() throws Exception {

        EventDto eventDto = EventDto.builder()
                .id(1L)
                .name("Test")
                .build();

        given(eventService.getEventById(any(Long.class))).willReturn(Optional.empty());

        mockMvc.perform(get(EventController.EVENT_PATH_ID,eventDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void saveEvent() throws Exception{

        EventDto eventDto = EventDto.builder()
                .id(1L)
                .name("Test")
                .build();
        EventRegistrationDto eventRDto = EventRegistrationDto.builder()
                .name("Test")
                .participantSet(Set.of())
                .build();

        given(eventService.createEvent(eventRDto)).willReturn(eventDto);

        mockMvc.perform(post(EventController.EVENT_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventRDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(eventDto.getId().intValue())))
                .andExpect(jsonPath("$.name",is(eventDto.getName())));
    }

    @Test
    void updateEvent() throws Exception {

        EventDto eventDto = EventDto.builder()
                .id(1L)
                .name("Test")
                .build();
        EventRegistrationDto eventRDto = EventRegistrationDto.builder()
                .name("Test")
                .participantSet(Set.of())
                .build();

        given(eventService.updateEvent(any(Long.class),any(Event.class))).willReturn(Optional.of(eventDto));

        mockMvc.perform(put(EventController.EVENT_PATH_ID,eventDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventRDto)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(eventDto.getId().intValue())))
                .andExpect(jsonPath("$.name",is(eventDto.getName())));
    }

    @Test
    void updateEventWhenEventNotFound() throws Exception {

        EventDto eventDto = EventDto.builder()
                .id(1L)
                .name("Test")
                .build();
        EventRegistrationDto eventRDto = EventRegistrationDto.builder()
                .name("Test")
                .participantSet(Set.of())
                .build();

        given(eventService.updateEvent(any(Long.class),any(Event.class))).willReturn(Optional.empty());

        mockMvc.perform(put(EventController.EVENT_PATH_ID,eventDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventRDto)))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteEvent() throws Exception {

        given(eventService.deleteEventById(any(Long.class))).willReturn(true);

        mockMvc.perform(delete(EventController.EVENT_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteEventWhenEventNotFound() throws Exception {

        given(eventService.deleteEventById(any(Long.class))).willReturn(false);

        mockMvc.perform(delete(EventController.EVENT_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }
}