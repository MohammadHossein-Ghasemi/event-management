package co.muhu.eventManagement.mappers.organizer;

import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.model.OrganizerRegistrationDto;

public interface OrganizerMapper {
    Organizer organizerRegistrationDtoToOrganizer(OrganizerRegistrationDto organizerRegistrationDto);
}
