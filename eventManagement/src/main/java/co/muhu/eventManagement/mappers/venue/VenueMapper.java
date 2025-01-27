package co.muhu.eventManagement.mappers.venue;

import co.muhu.eventManagement.entity.Venue;
import co.muhu.eventManagement.mappers.event.EventMapper;
import co.muhu.eventManagement.model.EventDto;
import co.muhu.eventManagement.model.VenueDto;
import co.muhu.eventManagement.model.VenueRegistrationDto;

import java.util.List;

public class VenueMapper{

    public static Venue venueRegistrationDtoToVenue(VenueRegistrationDto venueRegistrationDto) {
        if (venueRegistrationDto==null){
            return new Venue();
        }
        return Venue.builder()
                .name(venueRegistrationDto.getName())
                .address(venueRegistrationDto.getAddress())
                .capacity(venueRegistrationDto.getCapacity())
                .build();
    }

    public static VenueDto venueToVenueDto(Venue venue){
        if (venue==null){
            return new VenueDto();
        }
        List<EventDto> eventDtoList = venue.getEventSet()
                .stream()
                .map(EventMapper::eventToEventDto)
                .toList();

        return VenueDto.builder()
                .id(venue.getId())
                .name(venue.getName())
                .address(venue.getAddress())
                .capacity(venue.getCapacity())
                .eventSet(eventDtoList)
                .build();

    }
}
