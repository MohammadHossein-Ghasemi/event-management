package co.muhu.eventManagement.mappers.feedback;

import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.model.FeedBackRegistrationDto;

public interface FeedBackMapper {
    FeedBack feedBackRegistrationDtoToFeedBack(FeedBackRegistrationDto feedBackRegistrationDto);
}
