package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.model.OrganizerRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface OrganizerService {
    List<Organizer> getAllOrganizers();
    Optional<Organizer> getOrganizerById(Long id);
    Organizer createOrganizer(OrganizerRegistrationDto organizerRegistrationDto);
    Optional<Organizer> updateOrganizer(Long id, Organizer organizer);
    boolean deleteOrganizerById(Long id);
    List<Organizer> getOrganizerByEventId(Long eventId);
}
