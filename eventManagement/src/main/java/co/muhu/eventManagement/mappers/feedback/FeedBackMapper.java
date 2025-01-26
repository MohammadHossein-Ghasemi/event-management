package co.muhu.eventManagement.mappers.feedback;

import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.model.FeedBackDto;
import co.muhu.eventManagement.model.FeedBackRegistrationDto;

public class FeedBackMapper {

    public static FeedBack feedBackRegistrationDtoToFeedBack(FeedBackRegistrationDto feedBackRegistrationDto) {
        return FeedBack.builder()
                .rating(feedBackRegistrationDto.getRating())
                .comments(feedBackRegistrationDto.getComments())
                .event(feedBackRegistrationDto.getEvent())
                .participant(feedBackRegistrationDto.getParticipant())
                .build();
    }

    public static FeedBackDto feedBackToFeedBackDto(FeedBack feedBack) {
        return FeedBackDto.builder()
                .id(feedBack.getId())
                .rating(feedBack.getRating())
                .comments(feedBack.getComments())
                .submittedDate(feedBack.getSubmittedDate())
                .eventId(feedBack.getEvent().getId())
                .participantId(feedBack.getParticipant().getId())
                .build();
    }
}
