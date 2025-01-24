package co.muhu.eventManagement.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegistrationDto {
    @NotNull
    @NotBlank
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "First name cannot be blank.")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank.")
    private String lastName;
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",message = "Invalid phone number.")
    private String phoneNumber;

}
