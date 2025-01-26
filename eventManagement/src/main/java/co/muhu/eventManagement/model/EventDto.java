package co.muhu.eventManagement.model;

import co.muhu.eventManagement.entity.FeedBack;
import co.muhu.eventManagement.entity.Participant;
import co.muhu.eventManagement.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private Long memberId;
    private Long organizerId;
    private Long venueId;
    private List<ParticipantDto> participantIds;
    private List<TicketDto> ticketIds;
    private List<FeedBackDto> feedbackIds;
}
