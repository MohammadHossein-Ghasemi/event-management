package co.muhu.eventManagement.mappers;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.model.EventRegistrationDto;

public interface EventMapper {
    Event EventRegistrationDtoToEvent(EventRegistrationDto eventRegistrationDto);
}
