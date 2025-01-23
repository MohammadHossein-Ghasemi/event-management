package co.muhu.eventManagement.mappers;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.model.EventRegistrationDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T22:22:03+0330",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
public class EventMapperImpl implements EventMapper {

    @Override
    public Event EventRegistrationDtoToEvent(EventRegistrationDto eventRegistrationDto) {
        if ( eventRegistrationDto == null ) {
            return null;
        }

        Event event = new Event();

        return event;
    }
}
