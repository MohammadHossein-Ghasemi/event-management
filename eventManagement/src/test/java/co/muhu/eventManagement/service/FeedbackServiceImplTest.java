package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.model.FeedBackDto;
import co.muhu.eventManagement.model.FeedBackRegistrationDto;
import co.muhu.eventManagement.repository.EventRepository;
import co.muhu.eventManagement.repository.FeedBackRepository;
import co.muhu.eventManagement.repository.ParticipantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class FeedbackServiceImplTest {

    private FeedbackServiceImpl feedbackServiceTest;

    @Mock
    private FeedBackRepository feedBackRepositoryMock;
    @Mock
    private EventRepository eventRepositoryMock;
    @Mock
    private ParticipantRepository participantRepositoryMock;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        feedbackServiceTest=new FeedbackServiceImpl(feedBackRepositoryMock,eventRepositoryMock,participantRepositoryMock);
    }

    @AfterEach
    void afterEach() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllFeedbacks() {
        feedbackServiceTest.getAllFeedbacks();
        verify(feedBackRepositoryMock).findAll();
    }

    @Test
    void getFeedbackById() {
        long feedBackId=1;
        feedbackServiceTest.getFeedbackById(feedBackId);
        verify(feedBackRepositoryMock).findById(feedBackId);
    }

    @Test
    void createFeedback() {
        Event existedEvent = Event.builder().id((long)1).build();
        Participant existedParticipant = Participant.builder().id((long)10).build();
        FeedBackRegistrationDto newFeedBack= FeedBackRegistrationDto.builder()
                .event(existedEvent)
                .participant(existedParticipant)
                .build();

        when(eventRepositoryMock.existsById(newFeedBack.getEvent().getId())).thenReturn(true);
        when(eventRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(existedEvent));
        when(participantRepositoryMock.existsById(newFeedBack.getParticipant().getId())).thenReturn(true);
        when(participantRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(existedParticipant));

        feedbackServiceTest.createFeedback(newFeedBack);
        verify(feedBackRepositoryMock).save(any(FeedBack.class));
    }

    @Test
    void createFeedbackWhenEventNotPresent() {
        Event existedEvent = Event.builder().id((long)1).build();
        Participant existedParticipant = Participant.builder().id((long)10).build();
        FeedBackRegistrationDto newFeedBack= FeedBackRegistrationDto.builder()
                .event(existedEvent)
                .participant(existedParticipant)
                .build();

        when(eventRepositoryMock.existsById(newFeedBack.getEvent().getId())).thenReturn(false);
        when(eventRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(existedEvent));
        when(participantRepositoryMock.existsById(newFeedBack.getParticipant().getId())).thenReturn(true);
        when(participantRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(existedParticipant));

        assertThatThrownBy(()->
                feedbackServiceTest.createFeedback(newFeedBack))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Invalid event. Register the event first.");
    }

    @Test
    void createFeedbackWhenParticipantNotPresent() {
        Event existedEvent = Event.builder().id((long)1).build();
        Participant existedParticipant = Participant.builder().id((long)10).build();
        FeedBackRegistrationDto newFeedBack= FeedBackRegistrationDto.builder()
                .event(existedEvent)
                .participant(existedParticipant)
                .build();

        when(eventRepositoryMock.existsById(newFeedBack.getEvent().getId())).thenReturn(true);
        when(eventRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(existedEvent));
        when(participantRepositoryMock.existsById(newFeedBack.getParticipant().getId())).thenReturn(false);
        when(participantRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(existedParticipant));

        assertThatThrownBy(()->
                feedbackServiceTest.createFeedback(newFeedBack))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Invalid participant. Register the participant first.");
    }

    @Test
    void updateFeedback() {
        FeedBack existingFeedBack = FeedBack.builder().id(1L).comments("Old FeedBack").build();
        FeedBack updatedFeedBack = FeedBack.builder().id(existingFeedBack.getId()).comments("New FeedBack").build();

        when(feedBackRepositoryMock.findById(existingFeedBack.getId())).thenReturn(Optional.of(existingFeedBack));
        when(feedBackRepositoryMock.save(any(FeedBack.class))).thenReturn(updatedFeedBack);

        Optional<FeedBackDto> result = feedbackServiceTest.updateFeedback(existingFeedBack.getId(), updatedFeedBack);

        assertThat(result).isPresent();
        assertThat(result.get().getComments()).isEqualTo("New FeedBack");

        verify(feedBackRepositoryMock).findById(existingFeedBack.getId());
        verify(feedBackRepositoryMock).save(any(FeedBack.class));
    }

    @Test
    void updateFeedbackWhenFeedBackNotPresent() {
        FeedBack exitingFeedBack= FeedBack.builder().comments("Old FeedBack").id((long)1).build();
        FeedBack updatedFeedBack= FeedBack.builder().comments("New FeedBack").id(exitingFeedBack.getId()).build();

        when(feedBackRepositoryMock.findById(exitingFeedBack.getId())).thenReturn(Optional.empty());

        Optional<FeedBackDto> result = feedbackServiceTest.updateFeedback(exitingFeedBack.getId(), updatedFeedBack);

        assertThat(result).isNotPresent();
    }

    @Test
    void deleteFeedbackById() {
        long feedBackId=1;
        when(feedBackRepositoryMock.existsById(feedBackId)).thenReturn(true);
        feedbackServiceTest.deleteFeedbackById(feedBackId);
        verify(feedBackRepositoryMock).deleteById(feedBackId);
    }
    @Test
    void deleteFeedbackByIdWhenFeedBackNotPresent() {
        long feedBackId=1;
        when(feedBackRepositoryMock.existsById(feedBackId)).thenReturn(false);
        boolean result = feedbackServiceTest.deleteFeedbackById(feedBackId);
        assertThat(result).isFalse();
    }

    @Test
    void getAllFeedbacksByEventId() {
        long eventId=1;
        when(eventRepositoryMock.existsById(eventId)).thenReturn(true);
        feedbackServiceTest.getAllFeedbacksByEventId(eventId);
        verify(feedBackRepositoryMock).findAllByEventId(eventId);
    }
    @Test
    void getAllFeedbacksByEventIdWhenEventNotPresent() {
        long eventId=1;
        when(eventRepositoryMock.existsById(eventId)).thenReturn(false);

        assertThatThrownBy(()->feedbackServiceTest.getAllFeedbacksByEventId(eventId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("There is no event with this id : "+eventId);
    }

    @Test
    void getAllFeedBacksByParticipantId() {
        long participantId=1;
        when(participantRepositoryMock.existsById(participantId)).thenReturn(true);
        feedbackServiceTest.getAllFeedBacksByParticipantId(participantId);
        verify(feedBackRepositoryMock).findAllByParticipantId(participantId);
    }
    @Test
    void getAllFeedBacksByParticipantIdWhenParticipantNotPresent() {
        long participantId=1;
        when(participantRepositoryMock.existsById(participantId)).thenReturn(false);
        assertThatThrownBy(()->
                feedbackServiceTest.getAllFeedBacksByParticipantId(participantId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("There is no participant with this id : "+participantId);
    }
}