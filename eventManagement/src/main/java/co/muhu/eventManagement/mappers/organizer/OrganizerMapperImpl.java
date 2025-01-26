package co.muhu.eventManagement.mappers.organizer;

import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.model.OrganizerRegistrationDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizerMapperImpl implements OrganizerMapper {
    @Override
    public Organizer organizerRegistrationDtoToOrganizer(OrganizerRegistrationDto organizerRegistrationDto) {
        return Organizer.builder()
                .name(organizerRegistrationDto.getName())
                .contactInfo(organizerRegistrationDto.getContactInfo())
                .build();
    }
}
