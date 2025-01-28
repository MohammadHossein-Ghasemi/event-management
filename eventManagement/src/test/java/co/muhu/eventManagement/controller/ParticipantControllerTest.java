package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.ParticipantDto;
import co.muhu.eventManagement.model.ParticipantRegistrationDto;
import co.muhu.eventManagement.service.ParticipantService;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@WebMvcTest(ParticipantController.class)
class ParticipantControllerTest {

    @MockitoBean
    ParticipantService participantService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllParticipant() throws Exception {

        given(participantService.getAllParticipants()).willReturn(List.of());

        mockMvc.perform(get(ParticipantController.PARTICIPANT_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));

    }

    @Test
    void getParticipantById() throws Exception {

        ParticipantDto participantDto = ParticipantDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();

        given(participantService.getParticipantById(any(Long.class))).willReturn(Optional.of(participantDto));

        mockMvc.perform(get(ParticipantController.PARTICIPANT_PATH_ID,participantDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(participantDto.getId().intValue())))
                .andExpect(jsonPath("$.email",is(participantDto.getEmail())));
    }

    @Test
    void getParticipantByIdWhenParticipantNotFound() throws Exception {

        given(participantService.getParticipantById(any(Long.class))).willReturn(Optional.empty());

        mockMvc.perform(get(ParticipantController.PARTICIPANT_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }
    @Test
    void saveParticipant() throws Exception{

        ParticipantDto participantDto = ParticipantDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();
        ParticipantRegistrationDto participantRDto = ParticipantRegistrationDto.builder()
                .name("Test")
                .email("test@gmail.com")
                .build();

        given(participantService.createParticipant(participantRDto)).willReturn(participantDto);

        mockMvc.perform(post(ParticipantController.PARTICIPANT_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participantRDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(participantDto.getId().intValue())))
                .andExpect(jsonPath("$.email",is(participantDto.getEmail())));
    }

    @Test
    void updateParticipant() throws Exception {

        ParticipantDto participantDto = ParticipantDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();
        ParticipantRegistrationDto participantRDto = ParticipantRegistrationDto.builder()
                .name("Test")
                .email("test@gmail.com")
                .build();

        given(participantService.updateParticipant(any(Long.class),any(Participant.class))).willReturn(Optional.of(participantDto));

        mockMvc.perform(put(ParticipantController.PARTICIPANT_PATH_ID,participantDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participantRDto)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(participantDto.getId().intValue())))
                .andExpect(jsonPath("$.email",is(participantDto.getEmail())));
    }

    @Test
    void updateParticipantWhenParticipantNotFound() throws Exception {

        ParticipantDto participantDto = ParticipantDto.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();
        ParticipantRegistrationDto participantRDto = ParticipantRegistrationDto.builder()
                .name("Test")
                .email("test@gmail.com")
                .build();

        given(participantService.updateParticipant(any(Long.class),any(Participant.class))).willReturn(Optional.empty());

        mockMvc.perform(put(ParticipantController.PARTICIPANT_PATH_ID,participantDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(participantRDto)))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteParticipant() throws Exception {

        given(participantService.deleteParticipantById(any(Long.class))).willReturn(true);

        mockMvc.perform(delete(ParticipantController.PARTICIPANT_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteParticipantWhenParticipantNotFound() throws Exception {

        given(participantService.deleteParticipantById(any(Long.class))).willReturn(false);

        mockMvc.perform(delete(ParticipantController.PARTICIPANT_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getAllParticipantByEventId() throws Exception {

        given(participantService.getParticipantsByEventId(any(Long.class))).willReturn(List.of());

        mockMvc.perform(get(ParticipantController.PARTICIPANT_PATH+"/event/{eventId}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));;
    }

    @Test
    void getAllParticipantByEventIdWhenEventNotFound() throws Exception {

        given(participantService.getParticipantsByEventId(any(Long.class))).willThrow(new ResourceNotFoundException("There is no event with this id : "+1));

        mockMvc.perform(get(ParticipantController.PARTICIPANT_PATH+"/event/{eventId}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }
}