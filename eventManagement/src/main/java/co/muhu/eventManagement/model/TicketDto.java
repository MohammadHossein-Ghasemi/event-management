package co.muhu.eventManagement.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long id;
    private BigDecimal price;
    private String status;
    private LocalDateTime purchaseDate;
    private EventDto eventDto;
    private ParticipantDto participantDto;
}
