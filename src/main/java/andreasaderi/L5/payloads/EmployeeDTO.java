package andreasaderi.L5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeDTO(
        @NotBlank(message = "Username field can't be blank or null.")
        @Size(min = 3, message = "Username field must be at least 3 characters long.")
        String username,
        @NotBlank(message = "Name field can't be blank or null.")
        @Size(min = 3, message = "Name field must be at least 3 characters long.")
        String name,
        @NotBlank(message = "Surname field can't be blank or null.")
        @Size(min = 3, message = "Surname field must be at least 3 characters long.")
        String surname,
        @NotBlank(message = "Email field can't be blank or null.")
        @Email(message = "Email field must be filled with a correct email address.")
        String email,
        @NotBlank(message = "Password field can't be blank or null.")
        @Size(min = 8, message = "Password field must be at least 8 characters long.")
        String password

) {
}
