package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Venue;
import co.muhu.eventManagement.mappers.venue.VenueMapper;
import co.muhu.eventManagement.model.VenueRegistrationDto;
import co.muhu.eventManagement.repository.VenueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Override
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @Override
    public Optional<Venue> getVenueById(Long id) {
        return venueRepository.findById(id);
    }

    @Override
    public Venue createVenue(VenueRegistrationDto venueRegistrationDto) {
        Venue venue = venueMapper.venueRegistrationDtoToVenue(venueRegistrationDto);
        return venueRepository.save(venue);
    }

    @Override
    public Optional<Venue> updateVenue(Long id, Venue venue) {
        AtomicReference<Optional<Venue>> foundedVenue = new AtomicReference<>();

        venueRepository.findById(id).ifPresentOrElse(
                updateVenue->{
                    updateVenue.setAddress(venue.getAddress());
                    updateVenue.setName(venue.getName());
                    updateVenue.setCapacity(venue.getCapacity());
                    updateVenue.setEventSet(venue.getEventSet());
                    foundedVenue.set(Optional.of(venueRepository.save(updateVenue)));
                },
                ()->foundedVenue.set(Optional.empty())
        );
        return foundedVenue.get();
    }

    @Override
    public boolean deleteVenueById(Long id) {
        if (venueRepository.existsById(id)){
            venueRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
