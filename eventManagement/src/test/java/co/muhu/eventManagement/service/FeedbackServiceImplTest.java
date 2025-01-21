package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.repository.FeedBackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FeedbackServiceImplTest {

    private FeedbackServiceImpl feedbackServiceTest;

    @Mock
    private FeedBackRepository feedBackRepositoryMock;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        feedbackServiceTest=new FeedbackServiceImpl(feedBackRepositoryMock);
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
        FeedBack newFeedBack=new FeedBack();
        feedbackServiceTest.createFeedback(newFeedBack);
        verify(feedBackRepositoryMock).save(newFeedBack);
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