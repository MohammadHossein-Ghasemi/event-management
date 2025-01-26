package co.muhu.eventManagement.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackDto {
    private Long id;
    private int rating;
    private String comments;
    private LocalDateTime submittedDate;
    private EventDto event;
    private ParticipantDto participant;
}

