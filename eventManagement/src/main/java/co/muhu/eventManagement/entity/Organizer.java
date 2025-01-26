package co.muhu.eventManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "organizer")
@Table(name = "organizer")
public class Organizer {
    @Id
    @SequenceGenerator(sequenceName = "organizer_id_seq",name = "o_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "o_seq")
    private Long id;
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    private String contactInfo;

    @OneToMany(mappedBy = "organizer")
    private Set<Event> event;
}
