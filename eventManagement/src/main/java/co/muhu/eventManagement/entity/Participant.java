package co.muhu.eventManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "participant")
@Table(name = "participant")
public class Participant {
    @Id
    @SequenceGenerator(sequenceName = "participant_id_seq",name = "p_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "p_seq")
    private Long id;
    @NotBlank(message = "Name cannot be blank.")
    private String name;
    @Email(message = "Email should be valid.")
    private String email;
    private String phone;

    @CreationTimestamp
    private LocalDateTime registrationDate;
    private String status;

    @ManyToMany
    @JoinTable(name = "event_participant",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id"))
    private Set<Event> eventSet;

    @OneToMany(mappedBy = "participant")
    private Set<Ticket> ticketSet;

    @OneToMany (mappedBy = "participant")
    private Set<FeedBack> feedBackSet;
}
