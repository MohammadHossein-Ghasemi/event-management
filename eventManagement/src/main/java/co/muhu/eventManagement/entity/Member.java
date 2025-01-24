package co.muhu.eventManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
@Table(name = "member")
public class Member {
    @Id
    @SequenceGenerator(sequenceName = "member_id_seq",name = "m_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO , generator = "m_seq")
    private Long id;

    @NotNull
    @NotBlank
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "First name cannot be blank.")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank.")
    private String lastName;
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",message = "Invalid phone number.")
    private String phoneNumber;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private Set<Event> organizedEvents;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "event_member",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<Event> participatedEvents;

}
