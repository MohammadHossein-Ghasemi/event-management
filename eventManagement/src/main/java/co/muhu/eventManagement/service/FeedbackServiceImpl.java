package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.repository.EventRepository;
import co.muhu.eventManagement.repository.FeedBackRepository;
import co.muhu.eventManagement.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedBackRepository feedBackRepository;
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public List<FeedBack> getAllFeedbacks() {
        return feedBackRepository.findAll();
    }

    @Override
    public Optional<FeedBack> getFeedbackById(Long id) {
        return feedBackRepository.findById(id);
    }

    @Override
    public FeedBack createFeedback(FeedBack feedBack) {
        Long eventId = feedBack.getEvent().getId();
        Long participantId = feedBack.getParticipant().getId();
        if (!eventRepository.existsById(eventId)){
            throw new IllegalArgumentException("Event dose not exist.");
        }
        if (!participantRepository.existsById(participantId)){
            throw new IllegalArgumentException("Participant does not exist.");
        }
        return feedBackRepository.save(feedBack);
    }

    @Override
    public Optional<FeedBack> updateFeedback(Long id, FeedBack feedBack) {
        AtomicReference<Optional<FeedBack>> foundedFeedBack= new AtomicReference<>();
        feedBackRepository.findById(id).ifPresentOrElse(
                updateFeedback->{
                    updateFeedback.setComments(feedBack.getComments());
                    updateFeedback.setEvent(feedBack.getEvent());
                    updateFeedback.setRating(feedBack.getRating());
                    updateFeedback.setParticipant(feedBack.getParticipant());
                    updateFeedback.setSubmittedDate(feedBack.getSubmittedDate());
                    foundedFeedBack.set(Optional.of(feedBackRepository.save(updateFeedback)));
                },()-> foundedFeedBack.set(Optional.empty())
        );
        return foundedFeedBack.get();
    }

    @Override
    public boolean deleteFeedbackById(Long id) {
        if (feedBackRepository.existsById(id)) {
            feedBackRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<FeedBack> getAllFeedbacksByEventId(Long eventId) {
        if (!eventRepository.existsById(eventId)){
            throw new ResourceNotFoundException("There is no event with this id : "+eventId);
        }
        return feedBackRepository.findAllByEventId(eventId);
    }

    @Override
    public List<FeedBack> getAllFeedBacksByParticipantId(Long participantId) {
        if (!participantRepository.existsById(participantId)){
            throw new ResourceNotFoundException("There is no participant with this id : "+participantId);
        }
        return feedBackRepository.findAllByParticipantId(participantId);
    }
}
