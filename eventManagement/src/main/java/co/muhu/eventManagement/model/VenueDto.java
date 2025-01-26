package co.muhu.eventManagement.model;

import co.muhu.eventManagement.entity.Event;
import lombok.*;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VenueDto {
    private Long id;
    private String name;
    private String address;
    private String capacity;
    private List<EventDto> eventSet;
}
