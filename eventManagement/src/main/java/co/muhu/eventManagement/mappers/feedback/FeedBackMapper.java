package co.muhu.eventManagement.mappers.feedback;

import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.mappers.event.EventMapper;
import co.muhu.eventManagement.mappers.participant.ParticipantMapper;
import co.muhu.eventManagement.model.EventDto;
import co.muhu.eventManagement.model.FeedBackDto;
import co.muhu.eventManagement.model.FeedBackRegistrationDto;
import co.muhu.eventManagement.model.ParticipantDto;

import java.util.List;

public class FeedBackMapper {

    public static FeedBack feedBackRegistrationDtoToFeedBack(FeedBackRegistrationDto feedBackRegistrationDto) {
        if (feedBackRegistrationDto==null){
            return new FeedBack();
        }
        return FeedBack.builder()
                .rating(feedBackRegistrationDto.getRating())
                .comments(feedBackRegistrationDto.getComments())
                .event(feedBackRegistrationDto.getEvent())
                .participant(feedBackRegistrationDto.getParticipant())
                .build();
    }

    public static FeedBackDto feedBackToFeedBackDto(FeedBack feedBack) {
        if(feedBack==null){
            return new FeedBackDto();
        }
        EventDto eventDto = EventMapper.eventToEventDto(feedBack.getEvent());
        ParticipantDto participantDto = ParticipantMapper.participantToParticipantDto(feedBack.getParticipant());
        return FeedBackDto.builder()
                .id(feedBack.getId())
                .rating(feedBack.getRating())
                .comments(feedBack.getComments())
                .submittedDate(feedBack.getSubmittedDate())
                .event(eventDto)
                .participant(participantDto)
                .build();
    }
}
