package co.muhu.eventManagement.mappers.venue;

import co.muhu.eventManagement.entity.Venue;
import co.muhu.eventManagement.model.VenueRegistrationDto;
import org.springframework.stereotype.Component;

@Component
public class VenueMapperImpl implements VenueMapper {
    @Override
    public Venue venueRegistrationDtoToVenue(VenueRegistrationDto venueRegistrationDto) {
        return Venue.builder()
                .name(venueRegistrationDto.getName())
                .address(venueRegistrationDto.getAddress())
                .capacity(venueRegistrationDto.getCapacity())
                .build();
    }
}
