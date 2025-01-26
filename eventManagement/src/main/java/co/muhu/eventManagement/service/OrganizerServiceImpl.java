package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.mappers.organizer.OrganizerMapper;
import co.muhu.eventManagement.model.OrganizerDto;
import co.muhu.eventManagement.model.OrganizerRegistrationDto;
import co.muhu.eventManagement.repository.EventRepository;
import co.muhu.eventManagement.repository.OrganizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrganizerServiceImpl implements OrganizerService {
    private final OrganizerRepository organizerRepository;
    private final EventRepository eventRepository;

    @Override
    public List<OrganizerDto> getAllOrganizers() {
        return organizerRepository.findAll()
                .stream()
                .map(OrganizerMapper::organizerToOrganizerDto)
                .toList();
    }

    @Override
    public Optional<OrganizerDto> getOrganizerById(Long id) {

        return organizerRepository.findById(id)
                .map(OrganizerMapper::organizerToOrganizerDto);
    }

    @Override
    public OrganizerDto createOrganizer(OrganizerRegistrationDto organizerRegistrationDto) {
        Organizer organizer = OrganizerMapper.organizerRegistrationDtoToOrganizer(organizerRegistrationDto);
        return OrganizerMapper.organizerToOrganizerDto(organizerRepository.save(organizer));
    }

    @Override
    public Optional<OrganizerDto> updateOrganizer(Long id, Organizer organizer) {
        AtomicReference<Optional<OrganizerDto>> foundedOrganizer = new AtomicReference<>();

        organizerRepository.findById(id).ifPresentOrElse(
                updatedOrganizer->{
                    updatedOrganizer.setName(organizer.getName());
                    updatedOrganizer.setContactInfo(organizer.getContactInfo());
                    updatedOrganizer.setEvent(organizer.getEvent());
                    foundedOrganizer.set(Optional.of(OrganizerMapper.organizerToOrganizerDto(organizerRepository.save(updatedOrganizer))));
                },
                ()->foundedOrganizer.set(Optional.empty())
        );

        return foundedOrganizer.get();
    }

    @Override
    public boolean deleteOrganizerById(Long id) {
        if (organizerRepository.existsById(id)){
            organizerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<OrganizerDto> getOrganizerByEventId(Long eventId) {
        if (!eventRepository.existsById(eventId)){
            throw new ResourceNotFoundException("There is no event with this id : "+eventId);
        }
        return organizerRepository.findByEventId(eventId)
                .stream()
                .map(OrganizerMapper::organizerToOrganizerDto)
                .toList();
    }
}
