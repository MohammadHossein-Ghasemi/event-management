package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.FeedBack;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    List<FeedBack> getAllFeedbacks();
    Optional<FeedBack> getFeedbackById(Long id);
    FeedBack createFeedback(FeedBack feedBack);
    Optional<FeedBack> updateFeedback(Long id,FeedBack feedBack);
    boolean deleteFeedbackById(Long id);
    List<FeedBack> getAllFeedbacksByEventId(Long eventId);
    List<FeedBack> getAllFeedBacksByParticipantId(Long participantId);
}
