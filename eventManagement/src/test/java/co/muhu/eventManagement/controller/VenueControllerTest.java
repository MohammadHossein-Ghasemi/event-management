package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Venue;
import co.muhu.eventManagement.model.VenueDto;
import co.muhu.eventManagement.model.VenueRegistrationDto;
import co.muhu.eventManagement.service.VenueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.core.Is.is;

@WebMvcTest(VenueController.class)
class VenueControllerTest {

    @MockitoBean
    VenueService venueService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllVenues() throws Exception {
        given(venueService.getAllVenues())
                .willReturn(List.of());

        mockMvc.perform(get(VenueController.VENUE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(0)));
    }

    @Test
    void getVenueById() throws Exception {
        VenueDto venueDto = VenueDto.builder()
                .id(1L)
                .name("LA")
                .build();

        given(venueService.getVenueById(venueDto.getId())).willReturn(Optional.of(venueDto));

        mockMvc.perform(get(VenueController.VENUE_PATH_ID,venueDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(venueDto.getId().intValue())))
                .andExpect(jsonPath("$.name",is(venueDto.getName())));
    }

    @Test
    void getVenueByIdWhenVenueNotFound() throws Exception {

        given(venueService.getVenueById(any(Long.class))).willReturn(Optional.empty());

        mockMvc.perform(get(VenueController.VENUE_PATH_ID,any(Long.class))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveVenue() throws Exception {
        VenueRegistrationDto venueRDto= VenueRegistrationDto.builder()
                .name("LA")
                .address("USA")
                .build();

        given(venueService.createVenue(venueRDto)).willReturn(any(VenueDto.class));

        mockMvc.perform(post(VenueController.VENUE_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venueRDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateVenue() throws Exception {
        Venue venue = Venue.builder()
                .id(1L)
                .name("LA")
                .address("USA")
                .build();

        VenueDto venueDto = VenueDto.builder()
                .id(venue.getId())
                .name(venue.getName())
                .address(venue.getAddress())
                .build();

        given(venueService.updateVenue(any(Long.class),any(Venue.class)))
                .willReturn(Optional.of(venueDto));

        mockMvc.perform(put(VenueController.VENUE_PATH_ID,venue.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venue)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(venueDto.getId().intValue())))
                .andExpect(jsonPath("$.name",is(venueDto.getName())));
    }

    @Test
    void updateVenueWhenVenueNotFound() throws Exception {
        Venue venue = Venue.builder()
                .id(1L)
                .name("LA")
                .address("USA")
                .build();

        given(venueService.updateVenue(any(Long.class),any(Venue.class)))
                .willReturn(Optional.empty());

        mockMvc.perform(put(VenueController.VENUE_PATH_ID,venue.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venue)))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }
    @Test
    void deleteVenue() throws Exception {
        Long venueId = 1L;

        given(venueService.deleteVenueById(venueId)).willReturn(true);

        mockMvc.perform(delete(VenueController.VENUE_PATH_ID,venueId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Location"));
    }
    @Test
    void deleteVenueWhenVenueNotFound() throws Exception {
        Long venueId = 1L;

        given(venueService.deleteVenueById(venueId)).willReturn(false);

        mockMvc.perform(delete(VenueController.VENUE_PATH_ID,venueId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }
}