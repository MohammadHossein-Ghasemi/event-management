package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.exception.ResourceNotFoundException;
import co.muhu.eventManagement.mappers.organizer.OrganizerMapper;
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
    private final OrganizerMapper organizerMapper;
    @Override
    public List<Organizer> getAllOrganizers() {
        return organizerRepository.findAll();
    }

    @Override
    public Optional<Organizer> getOrganizerById(Long id) {

        return organizerRepository.findById(id);
    }

    @Override
    public Organizer createOrganizer(OrganizerRegistrationDto organizerRegistrationDto) {
        Organizer organizer = organizerMapper.organizerRegistrationDtoToOrganizer(organizerRegistrationDto);
        return organizerRepository.save(organizer);
    }

    @Override
    public Optional<Organizer> updateOrganizer(Long id, Organizer organizer) {
        AtomicReference<Optional<Organizer>> foundedOrganizer = new AtomicReference<>();

        organizerRepository.findById(id).ifPresentOrElse(
                updatedOrganizer->{
                    updatedOrganizer.setName(organizer.getName());
                    updatedOrganizer.setContactInfo(organizer.getContactInfo());
                    updatedOrganizer.setEvent(organizer.getEvent());
                    foundedOrganizer.set(Optional.of(organizerRepository.save(updatedOrganizer)));
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
    public List<Organizer> getOrganizerByEventId(Long eventId) {
        if (!eventRepository.existsById(eventId)){
            throw new ResourceNotFoundException("There is no event with this id : "+eventId);
        }
        return organizerRepository.findByEventId(eventId);
    }
}
