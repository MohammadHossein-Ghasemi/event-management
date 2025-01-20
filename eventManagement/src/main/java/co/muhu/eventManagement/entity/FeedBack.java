package co.muhu.eventManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
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

    @Size(min = 0,max = 10,message = "Rating out of range.")
    private int rating;

    private String comments;

    @CreationTimestamp
    private LocalDateTime submittedDate;

    @ManyToOne
    private Event event;

    @ManyToOne
    private Participant participant;
}
