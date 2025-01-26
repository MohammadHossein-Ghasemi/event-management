package co.muhu.eventManagement.model;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRegistrationDto {
    @Min(0)
    private BigDecimal price;

    private String status;
    @NotNull
    private Event event;
    @NotNull
    private Participant participant;
}
