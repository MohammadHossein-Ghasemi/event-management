package co.muhu.eventManagement.mappers.feedback;

import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.model.FeedBackRegistrationDto;

public class FeedBackMapperImpl implements FeedBackMapper {
    @Override
    public FeedBack feedBackRegistrationDtoToFeedBack(FeedBackRegistrationDto feedBackRegistrationDto) {
        return FeedBack.builder()
                .rating(feedBackRegistrationDto.getRating())
                .comments(feedBackRegistrationDto.getComments())
                .event(feedBackRegistrationDto.getEvent())
                .participant(feedBackRegistrationDto.getParticipant())
                .build();
    }
}
