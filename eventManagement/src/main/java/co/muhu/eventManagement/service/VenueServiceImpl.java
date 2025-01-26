package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Venue;
import co.muhu.eventManagement.mappers.venue.VenueMapper;
import co.muhu.eventManagement.model.VenueDto;
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

    @Override
    public List<VenueDto> getAllVenues() {
        return venueRepository.findAll()
                .stream()
                .map(VenueMapper::venueToVenueDto)
                .toList();
    }

    @Override
    public Optional<VenueDto> getVenueById(Long id) {

        return venueRepository.findById(id)
                .map(VenueMapper::venueToVenueDto);
    }

    @Override
    public VenueDto createVenue(VenueRegistrationDto venueRegistrationDto) {
        Venue venue = VenueMapper.venueRegistrationDtoToVenue(venueRegistrationDto);
        return VenueMapper.venueToVenueDto(venueRepository.save(venue));
    }

    @Override
    public Optional<VenueDto> updateVenue(Long id, Venue venue) {
        AtomicReference<Optional<VenueDto>> foundedVenue = new AtomicReference<>();

        venueRepository.findById(id).ifPresentOrElse(
                updateVenue->{
                    updateVenue.setAddress(venue.getAddress());
                    updateVenue.setName(venue.getName());
                    updateVenue.setCapacity(venue.getCapacity());
                    updateVenue.setEventSet(venue.getEventSet());
                    foundedVenue.set(Optional.of(VenueMapper.venueToVenueDto(venueRepository.save(updateVenue))));
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
