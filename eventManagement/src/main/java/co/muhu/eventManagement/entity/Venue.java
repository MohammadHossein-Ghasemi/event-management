package co.muhu.eventManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "venue")
@Table(name = "venue")
public class Venue {
    @Id
    @SequenceGenerator(sequenceName = "venue_id_seq",name = "v_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "v_seq")
    private Long id;
    @NotBlank(message = "Name cannot be blank.")
    private String name;
    private String address;
    private String capacity;

    @OneToMany(mappedBy = "venue")
    private Set<Event> eventSet;
}
