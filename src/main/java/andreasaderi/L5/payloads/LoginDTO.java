package andreasaderi.L5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "Email field can't be blank nor null")
        @Email
        String email,
        @NotBlank(message = "Password field can't be blank nor null")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password) {
}
