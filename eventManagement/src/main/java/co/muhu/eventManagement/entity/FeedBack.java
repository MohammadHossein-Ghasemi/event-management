package co.muhu.eventManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "feedback")
@Table(name = "feedback")
public class FeedBack {
    @Id
    @SequenceGenerator(sequenceName = "feedback_id_seq",name = "f_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "f_seq")
    private Long id;

    @Min(0)
    @Max(10)
    private int rating;

    private String comments;

    @CreationTimestamp
    private LocalDateTime submittedDate;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Participant participant;
}
