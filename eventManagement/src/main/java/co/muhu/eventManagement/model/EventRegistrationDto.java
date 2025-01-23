package co.muhu.eventManagement.model;

import co.muhu.eventManagement.entity.Member;
import co.muhu.eventManagement.entity.Organizer;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.entity.Venue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRegistrationDto {
    @NotBlank(message = "Name can not be blank.")
    private String name;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Member member;
    private Organizer organizer;
    private Venue venue;
    private Set<Participant> participantSet;
}
