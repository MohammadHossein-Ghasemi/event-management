package co.muhu.eventManagement.controller;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.FeedBackDto;
import co.muhu.eventManagement.model.FeedBackRegistrationDto;
import co.muhu.eventManagement.service.FeedbackService;
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

@WebMvcTest(FeedBackController.class)
class FeedBackControllerTest {

    @MockitoBean
    FeedbackService feedbackService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllFeedBacks() throws Exception {

        given(feedbackService.getAllFeedbacks()).willReturn(List.of());

        mockMvc.perform(get(FeedBackController.FEEDBACK_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));

    }

    @Test
    void getFeedBackById() throws Exception {

        FeedBackDto feedBackDto = FeedBackDto.builder()
                .id(1L)
                .rating(9)
                .comments("Good")
                .build();

        given(feedbackService.getFeedbackById(any(Long.class))).willReturn(Optional.of(feedBackDto));

        mockMvc.perform(get(FeedBackController.FEEDBACK_PATH_ID,feedBackDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(feedBackDto.getId().intValue())))
                .andExpect(jsonPath("$.comments",is(feedBackDto.getComments())));
    }

    @Test
    void getFeedBackByIdWhenFeedBackNotFound() throws Exception {

        given(feedbackService.getFeedbackById(any(Long.class))).willReturn(Optional.empty());

        mockMvc.perform(get(FeedBackController.FEEDBACK_PATH_ID,1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }
    @Test
    void saveFeedBack() throws Exception{

        FeedBackDto feedBackDto = FeedBackDto.builder()
                .id(1L)
                .rating(9)
                .comments("Good")
                .build();

        FeedBackRegistrationDto feedBackRDto = FeedBackRegistrationDto.builder()
                .rating(9)
                .comments("Good")
                .event(new Event())
                .participant(new Participant())
                .build();

        given(feedbackService.createFeedback(feedBackRDto)).willReturn(feedBackDto);

        mockMvc.perform(post(FeedBackController.FEEDBACK_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(feedBackRDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateFeedBack() throws Exception {

        FeedBackDto feedBackDto = FeedBackDto.builder()
                .id(1L)
                .rating(9)
                .comments("Good")
                .build();

        given(feedbackService.updateFeedback(any(Long.class),any(FeedBack.class))).willReturn(Optional.of(feedBackDto));

        mockMvc.perform(put(FeedBackController.FEEDBACK_PATH_ID,feedBackDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FeedBack())))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id",is(feedBackDto.getId().intValue())))
                .andExpect(jsonPath("$.comments",is(feedBackDto.getComments())));
    }

    @Test
    void updateFeedBackWhenFeedBackNotFound() throws Exception {

        FeedBackDto feedBackDto = FeedBackDto.builder()
                .id(1L)
                .rating(9)
                .comments("Good")
                .build();

        given(feedbackService.updateFeedback(any(Long.class),any(FeedBack.class))).willReturn(Optional.empty());

        mockMvc.perform(put(FeedBackController.FEEDBACK_PATH_ID,feedBackDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new FeedBack())))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteFeedbackById() throws Exception {

        given(feedbackService.deleteFeedbackById(any(Long.class))).willReturn(true);

        mockMvc.perform(delete(FeedBackController.FEEDBACK_PATH_ID,1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Location"));
    }

    @Test
    void deleteFeedbackByIdWhenFeedBackNotFound() throws Exception {

        given(feedbackService.deleteFeedbackById(any(Long.class))).willReturn(false);

        mockMvc.perform(delete(FeedBackController.FEEDBACK_PATH_ID,1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getAllFeedBacksByEventId() throws Exception {

        given(feedbackService.getAllFeedbacksByEventId(any(Long.class))).willReturn(List.of());

        mockMvc.perform(get(FeedBackController.FEEDBACK_PATH+"/event/{eventId}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));
    }

    @Test
    void getAllFeedBacksByEventIdWhenEventNotFound() throws Exception {

        given(feedbackService.getAllFeedbacksByEventId(any(Long.class))).willThrow(new ResourceNotFoundException("There is no event with this id : "+1));

        mockMvc.perform(get(FeedBackController.FEEDBACK_PATH+"/event/{eventId}",1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getAllFeedBacksByParticipantId() throws Exception {

        given(feedbackService.getAllFeedBacksByParticipantId(any(Long.class))).willReturn(List.of());

        mockMvc.perform(get(FeedBackController.FEEDBACK_PATH+"/participant/{participantId}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.length()",is(0)));
    }

    @Test
    void getAllFeedBacksByParticipantIdWhenParticipantNotFound() throws Exception {

        given(feedbackService.getAllFeedBacksByParticipantId(any(Long.class))).willThrow(new ResourceNotFoundException("There is no participant with this id : "+1));

        mockMvc.perform(get(FeedBackController.FEEDBACK_PATH+"/participant/{participantId}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(header().exists("Location"));
    }
}