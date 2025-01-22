package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Organizer;
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
    @Override
    public List<Organizer> getAllOrganizers() {
        return organizerRepository.findAll();
    }

    @Override
    public Optional<Organizer> getOrganizerById(Long id) {

        return organizerRepository.findById(id);
    }

    @Override
    public Organizer createOrganizer(Organizer organizer) {
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
    public Optional<Organizer> getOrganizerByEventId(Long eventId) {
        return organizerRepository.findByEventId(eventId);
    }
}
