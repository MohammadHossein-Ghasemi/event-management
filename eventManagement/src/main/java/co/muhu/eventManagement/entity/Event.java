package co.muhu.eventManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "event")
@Table(name = "event")
public class Event {
    @Id
    @SequenceGenerator(sequenceName = "event_id_seq", name = "e_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "e_seq")
    private Long id;
    @NotBlank(message = "Name can not be blank.")
    private String name;

    private String description;

    private String location;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @CreationTimestamp
    private LocalDateTime createdTime;
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @ManyToOne
    private Member member;

    @ManyToMany
    @JoinTable(name = "event_member",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Member> memberset;

    @ManyToMany
    @JoinTable(name = "event_participant",
            joinColumns = @JoinColumn(name = "participant_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Participant> participantSet;

    @ManyToOne
    private Organizer organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Ticket> ticketSet;

    @ManyToOne
    private Venue venue;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<FeedBack> feedBackSet;
}
