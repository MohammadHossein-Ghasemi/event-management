package co.muhu.eventManagement.mappers.organizer;

import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.mappers.event.EventMapper;
import co.muhu.eventManagement.model.EventDto;
import co.muhu.eventManagement.model.OrganizerDto;
import co.muhu.eventManagement.model.OrganizerRegistrationDto;

import java.util.List;


public class OrganizerMapper{

    public static Organizer organizerRegistrationDtoToOrganizer(OrganizerRegistrationDto organizerRegistrationDto) {
        if(organizerRegistrationDto==null){
            return new Organizer();
        }
        return Organizer.builder()
                .name(organizerRegistrationDto.getName())
                .contactInfo(organizerRegistrationDto.getContactInfo())
                .build();
    }
    public static OrganizerDto organizerToOrganizerDto(Organizer organizer){
        if (organizer==null){
            return new OrganizerDto();
        }
        List<EventDto> eventDtoList = organizer.getEvent()
                .stream()
                .map(EventMapper::eventToEventDto)
                .toList();
        return OrganizerDto.builder()
                .id(organizer.getId())
                .name(organizer.getName())
                .contactInfo(organizer.getContactInfo())
                .event(eventDtoList)
                .build();
    }
}
