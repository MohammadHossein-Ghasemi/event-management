package co.muhu.eventManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ticket")
@Table(name = "ticket")
public class Ticket {
    @Id
    @SequenceGenerator(sequenceName = "ticket_id_seq",name = "t_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "t_seq")
    private Long id;
    @Min(0)
    private BigDecimal price;

    private String status;
    @CreationTimestamp
    private LocalDateTime purchaseDate;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Participant participant;

}
