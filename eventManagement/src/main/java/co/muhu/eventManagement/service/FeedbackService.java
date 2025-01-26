package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.model.FeedBackDto;
import co.muhu.eventManagement.model.FeedBackRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    List<FeedBackDto> getAllFeedbacks();
    Optional<FeedBackDto> getFeedbackById(Long id);
    FeedBackDto createFeedback(FeedBackRegistrationDto feedBackRegistrationDto);
    Optional<FeedBackDto> updateFeedback(Long id,FeedBack feedBack);
    boolean deleteFeedbackById(Long id);
    List<FeedBackDto> getAllFeedbacksByEventId(Long eventId);
    List<FeedBackDto> getAllFeedBacksByParticipantId(Long participantId);
}
