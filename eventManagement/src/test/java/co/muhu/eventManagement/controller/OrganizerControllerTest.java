package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.OrganizerDto;
import co.muhu.eventManagement.model.OrganizerRegistrationDto;
import co.muhu.eventManagement.service.OrganizerService;
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

@WebMvcTest(OrganizerController.class)
class OrganizerControllerTest {

    @MockitoBean
    OrganizerService organizerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllOrganizers() throws Exception {

        given(organizerService.getAllOrganizers()).willReturn(List.of());

        mockMvc.perform(get(OrganizerController.ORGANIZER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));

    }

    @Test
    void getOrganizerById() throws Exception {

        OrganizerDto organizerDto = OrganizerDto.builder()
                .id(1L)
                .name("Test")
                .build();

        given(organizerService.getOrganizerById(any(Long.class))).willReturn(Optional.of(organizerDto));

        mockMvc.perform(get(OrganizerController.ORGANIZER_PATH_ID,organizerDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(organizerDto.getId().intValue())))
                .andExpect(jsonPath("$.name",is(organizerDto.getName())));
    }

    @Test
    void getOrganizerByIdWhenOrganizerNotFound() throws Exception {

        OrganizerDto organizerDto = OrganizerDto.builder()
                .id(1L)
                .name("Test")
                .build();

        given(organizerService.getOrganizerById(any(Long.class))).willReturn(Optional.empty());

        mockMvc.perform(get(OrganizerController.ORGANIZER_PATH_ID,organizerDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void saveOrganizer() throws Exception{

        OrganizerDto organizerDto = OrganizerDto.builder()
                .id(1L)
                .name("Test")
                .build();
        OrganizerRegistrationDto organizerRDto = OrganizerRegistrationDto.builder()
                .name("Test")
                .build();

        given(organizerService.createOrganizer(organizerRDto)).willReturn(organizerDto);

        mockMvc.perform(post(OrganizerController.ORGANIZER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(organizerRDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(organizerDto.getId().intValue())))
                .andExpect(jsonPath("$.name",is(organizerDto.getName())));
    }

    @Test
    void updateOrganizer() throws Exception {

        OrganizerDto organizerDto = OrganizerDto.builder()
                .id(1L)
                .name("Test")
                .build();
        OrganizerRegistrationDto organizerRDto = OrganizerRegistrationDto.builder()
                .name("Test")
                .build();

        given(organizerService.updateOrganizer(any(Long.class),any(Organizer.class))).willReturn(Optional.of(organizerDto));

        mockMvc.perform(put(OrganizerController.ORGANIZER_PATH_ID,organizerDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(organizerRDto)))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(organizerDto.getId().intValue())))
                .andExpect(jsonPath("$.name",is(organizerDto.getName())));

    }

    @Test
    void updateOrganizerWhenOrganizerNotFound() throws Exception {

        OrganizerDto organizerDto = OrganizerDto.builder()
                .id(1L)
                .name("Test")
                .build();
        OrganizerRegistrationDto organizerRDto = OrganizerRegistrationDto.builder()
                .name("Test")
                .build();

        given(organizerService.updateOrganizer(any(Long.class),any(Organizer.class))).willReturn(Optional.empty());

        mockMvc.perform(put(OrganizerController.ORGANIZER_PATH_ID,organizerDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(organizerRDto)))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));

    }

    @Test
    void deletedOrganizer() throws Exception {

        given(organizerService.deleteOrganizerById(any(Long.class))).willReturn(true);

        mockMvc.perform(delete(OrganizerController.ORGANIZER_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Location"));

    }

    @Test
    void deletedOrganizerWhenOrganizerNotFound() throws Exception {

        given(organizerService.deleteOrganizerById(any(Long.class))).willReturn(false);

        mockMvc.perform(delete(OrganizerController.ORGANIZER_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));

    }

    @Test
    void getOrganizerByEventId() throws Exception {

        given(organizerService.getOrganizerByEventId(any(Long.class))).willReturn(List.of());

        mockMvc.perform(get(OrganizerController.ORGANIZER_PATH+"/event/{eventId}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));;
    }

    @Test
    void getOrganizerByEventIdWhenEventNotFound() throws Exception {

        given(organizerService.getOrganizerByEventId(any(Long.class))).willThrow(new ResourceNotFoundException("There is no event with this id : "+1));

        mockMvc.perform(get(OrganizerController.ORGANIZER_PATH+"/event/{eventId}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }
}