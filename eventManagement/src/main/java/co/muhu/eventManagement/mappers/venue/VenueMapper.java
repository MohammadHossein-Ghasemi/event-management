package co.muhu.eventManagement.mappers.venue;

import co.muhu.eventManagement.entity.Venue;
import co.muhu.eventManagement.model.VenueRegistrationDto;

public interface VenueMapper {
    Venue venueRegistrationDtoToVenue(VenueRegistrationDto venueRegistrationDto);
}
