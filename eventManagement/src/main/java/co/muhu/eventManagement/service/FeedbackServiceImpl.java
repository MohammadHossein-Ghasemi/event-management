package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.mappers.feedback.FeedBackMapper;
import co.muhu.eventManagement.model.FeedBackDto;
import co.muhu.eventManagement.model.FeedBackRegistrationDto;
import co.muhu.eventManagement.repository.EventRepository;
import co.muhu.eventManagement.repository.FeedBackRepository;
import co.muhu.eventManagement.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedBackRepository feedBackRepository;
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public List<FeedBackDto> getAllFeedbacks() {
        return feedBackRepository.findAll()
                .stream()
                .map(FeedBackMapper::feedBackToFeedBackDto)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<FeedBackDto> getFeedbackById(Long id) {
        return feedBackRepository.findById(id).map(FeedBackMapper::feedBackToFeedBackDto);
    }

    @Override
    public FeedBackDto createFeedback(FeedBackRegistrationDto feedBackRegistrationDto) {
        Event event = checkingFeedBackEventValidate(feedBackRegistrationDto)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid event. Register the event first."));
        Participant participant = checkingFeedBackParticipantValidate(feedBackRegistrationDto)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid participant. Register the participant first."));

        FeedBack feedBack = FeedBackMapper.feedBackRegistrationDtoToFeedBack(feedBackRegistrationDto);
        feedBack.setEvent(event);
        feedBack.setParticipant(participant);

        return FeedBackMapper.feedBackToFeedBackDto(feedBackRepository.save(feedBack));
    }

    private Optional<Event> checkingFeedBackEventValidate(FeedBackRegistrationDto feedBackRegistrationDto){
        if ((feedBackRegistrationDto.getEvent()==null)||(feedBackRegistrationDto.getEvent().getId()==null)||(!eventRepository.existsById(feedBackRegistrationDto.getEvent().getId()))){
            return Optional.empty();
        }
        return eventRepository.findById(feedBackRegistrationDto.getEvent().getId());
    }
    private Optional<Participant> checkingFeedBackParticipantValidate(FeedBackRegistrationDto feedBackRegistrationDto){
        if ((feedBackRegistrationDto.getParticipant()==null)||(feedBackRegistrationDto.getParticipant().getId()==null)||(!participantRepository.existsById(feedBackRegistrationDto.getParticipant().getId()))){
            return Optional.empty();
        }
        return participantRepository.findById(feedBackRegistrationDto.getParticipant().getId());
    }

    @Override
    public Optional<FeedBackDto> updateFeedback(Long id, FeedBack feedBack) {
        AtomicReference<Optional<FeedBackDto>> foundedFeedBack= new AtomicReference<>();
        feedBackRepository.findById(id).ifPresentOrElse(
                updateFeedback->{
                    updateFeedback.setComments(feedBack.getComments());
                    updateFeedback.setEvent(feedBack.getEvent());
                    updateFeedback.setRating(feedBack.getRating());
                    updateFeedback.setParticipant(feedBack.getParticipant());
                    updateFeedback.setSubmittedDate(feedBack.getSubmittedDate());
                    foundedFeedBack.set(Optional.of(FeedBackMapper.feedBackToFeedBackDto(feedBackRepository.save(updateFeedback))));
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
    public List<FeedBackDto> getAllFeedbacksByEventId(Long eventId) {
        if (!eventRepository.existsById(eventId)){
            throw new ResourceNotFoundException("There is no event with this id : "+eventId);
        }
        return feedBackRepository.findAllByEventId(eventId).stream()
                .map(FeedBackMapper::feedBackToFeedBackDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedBackDto> getAllFeedBacksByParticipantId(Long participantId) {
        if (!participantRepository.existsById(participantId)){
            throw new ResourceNotFoundException("There is no participant with this id : "+participantId);
        }
        return feedBackRepository.findAllByParticipantId(participantId).stream()
                .map(FeedBackMapper::feedBackToFeedBackDto)
                .collect(Collectors.toList());
    }
}
