package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Venue;

import java.util.List;
import java.util.Optional;

public interface VenueService {
    List<Venue> getAllVenues();
    Optional<Venue> getVenueById(Long id);
    Venue createVenue(Venue venue);
    Optional<Venue> updateVenue(Long id, Venue venue);
    boolean deleteVenueById(Long id);
}
