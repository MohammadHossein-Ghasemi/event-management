package co.muhu.eventManagement.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerDto {
    private Long id;
    private String name;
    private String contactInfo;
    private List<EventDto> event;
}
