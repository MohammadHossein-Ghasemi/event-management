package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Venue;
import co.muhu.eventManagement.model.VenueDto;
import co.muhu.eventManagement.model.VenueRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface VenueService {
    List<VenueDto> getAllVenues();
    Optional<VenueDto> getVenueById(Long id);
    VenueDto createVenue(VenueRegistrationDto venueRegistrationDto);
    Optional<VenueDto> updateVenue(Long id, Venue venue);
    boolean deleteVenueById(Long id);
}
