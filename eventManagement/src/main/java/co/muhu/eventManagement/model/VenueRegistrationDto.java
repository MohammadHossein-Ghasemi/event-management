package co.muhu.eventManagement.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VenueRegistrationDto {
    @NotBlank(message = "Name cannot be blank.")
    private String name;
    private String address;
    private String capacity;
}
