package co.muhu.eventManagement.service;

import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.model.OrganizerDto;
import co.muhu.eventManagement.model.OrganizerRegistrationDto;

import java.util.List;
import java.util.Optional;

public interface OrganizerService {
    List<OrganizerDto> getAllOrganizers();
    Optional<OrganizerDto> getOrganizerById(Long id);
    OrganizerDto createOrganizer(OrganizerRegistrationDto organizerRegistrationDto);
    Optional<OrganizerDto> updateOrganizer(Long id, Organizer organizer);
    boolean deleteOrganizerById(Long id);
    List<OrganizerDto> getOrganizerByEventId(Long eventId);
}
