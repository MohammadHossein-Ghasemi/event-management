package co.muhu.eventManagement.model;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    private Long eventId;
    private Long participantId;
}
