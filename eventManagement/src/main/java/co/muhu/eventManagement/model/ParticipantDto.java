package co.muhu.eventManagement.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime registrationDate;
    private String status;
    private List<EventDto> eventSet;
    private List<TicketDto> ticketSet;
    private List<FeedBackDto> feedBackSet;
}
