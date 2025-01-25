package co.muhu.eventManagement.mappers.event;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.model.EventRegistrationDto;
import org.springframework.stereotype.Component;

@Component
public class EventMapperImpl implements EventMapper {
    @Override
    public Event EventRegistrationDtoToEvent(EventRegistrationDto eventRegistrationDto) {
        return Event.builder()
                .name(eventRegistrationDto.getName())
                .description(eventRegistrationDto.getDescription())
                .location(eventRegistrationDto.getLocation())
                .startTime(eventRegistrationDto.getStartTime())
                .endTime(eventRegistrationDto.getEndTime())
                .member(eventRegistrationDto.getMember())
                .organizer(eventRegistrationDto.getOrganizer())
                .venue(eventRegistrationDto.getVenue())
                .participantSet(eventRegistrationDto.getParticipantSet())
                .build();
    }
}
