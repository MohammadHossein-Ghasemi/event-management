package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.model.FeedBackRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    List<FeedBack> getAllFeedbacks();
    Optional<FeedBack> getFeedbackById(Long id);
    FeedBack createFeedback(FeedBackRegistrationDto feedBackRegistrationDto);
    Optional<FeedBack> updateFeedback(Long id,FeedBack feedBack);
    boolean deleteFeedbackById(Long id);
    List<FeedBack> getAllFeedbacksByEventId(Long eventId);
    List<FeedBack> getAllFeedBacksByParticipantId(Long participantId);
}
