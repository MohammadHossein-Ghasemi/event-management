package co.muhu.eventManagement.model;

import co.muhu.eventManagement.entity.Event;
import co.muhu.eventManagement.entity.Participant;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedBackRegistrationDto {
    @Min(0)
    @Max(10)
    private int rating;
    private String comments;
    @NotNull
    private Event event;
    @NotNull
    private Participant participant;
}
