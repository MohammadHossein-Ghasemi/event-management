package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.entity.Participant;
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
        FeedBack newFeedBack= FeedBack.builder()
                .event(existedEvent)
                .participant(existedParticipant)
                .build();

        when(eventRepositoryMock.existsById(newFeedBack.getEvent().getId())).thenReturn(true);
        when(participantRepositoryMock.existsById(newFeedBack.getParticipant().getId())).thenReturn(true);

        feedbackServiceTest.createFeedback(newFeedBack);
        verify(feedBackRepositoryMock).save(newFeedBack);
    }
    @Test
    void createFeedbackWhenEventNotPresent() {
        Event existedEvent = Event.builder().id((long)1).build();
        Participant existedParticipant = Participant.builder().id((long)10).build();
        FeedBack newFeedBack= FeedBack.builder()
                .event(existedEvent)
                .participant(existedParticipant)
                .build();

        when(eventRepositoryMock.existsById(newFeedBack.getEvent().getId())).thenReturn(false);
        when(participantRepositoryMock.existsById(newFeedBack.getParticipant().getId())).thenReturn(true);

        assertThatThrownBy(()->
                feedbackServiceTest.createFeedback(newFeedBack))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Event dose not exist.");
    }
    @Test
    void createFeedbackWhenParticipantNotPresent() {
        Event existedEvent = Event.builder().id((long)1).build();
        Participant existedParticipant = Participant.builder().id((long)10).build();
        FeedBack newFeedBack= FeedBack.builder()
                .event(existedEvent)
                .participant(existedParticipant)
                .build();

        when(eventRepositoryMock.existsById(newFeedBack.getEvent().getId())).thenReturn(true);
        when(participantRepositoryMock.existsById(newFeedBack.getParticipant().getId())).thenReturn(false);

        assertThatThrownBy(()->
                feedbackServiceTest.createFeedback(newFeedBack))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Participant does not exist.");
    }
    @Test
    void updateFeedback() {
        FeedBack exitingFeedBack= FeedBack.builder().comments("Old FeedBack").id((long)1).build();
        FeedBack updatedFeedBack= FeedBack.builder().comments("New FeedBack").id(exitingFeedBack.getId()).build();

        when(feedBackRepositoryMock.findById(exitingFeedBack.getId())).thenReturn(Optional.of(exitingFeedBack));
        when(feedBackRepositoryMock.save(updatedFeedBack)).thenReturn(updatedFeedBack);

        Optional<FeedBack> result = feedbackServiceTest.updateFeedback(exitingFeedBack.getId(), updatedFeedBack);

        assertThat(result).isPresent();
        assertThat(result.get().getComments()).isEqualTo(updatedFeedBack.getComments());

    }
    @Test
    void updateFeedbackWhenFeedBackNotPresent() {
        FeedBack exitingFeedBack= FeedBack.builder().comments("Old FeedBack").id((long)1).build();
        FeedBack updatedFeedBack= FeedBack.builder().comments("New FeedBack").id(exitingFeedBack.getId()).build();

        when(feedBackRepositoryMock.findById(exitingFeedBack.getId())).thenReturn(Optional.empty());

        Optional<FeedBack> result = feedbackServiceTest.updateFeedback(exitingFeedBack.getId(), updatedFeedBack);

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
        feedbackServiceTest.getAllFeedbacksByEventId(eventId);
        verify(feedBackRepositoryMock).findAllByEventId(eventId);
    }

    @Test
    void getAllFeedBacksByParticipantId() {
        long participantId=1;
        feedbackServiceTest.getAllFeedBacksByParticipantId(participantId);
        verify(feedBackRepositoryMock).findAllByParticipantId(participantId);
    }
}