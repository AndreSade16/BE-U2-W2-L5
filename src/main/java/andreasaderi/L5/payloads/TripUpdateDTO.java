package andreasaderi.L5.payloads;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TripUpdateDTO(
        @NotBlank(message = "Destination field can't be null or blank")
        @Size(min = 3, message = "Destination field has to be at least 3 characters long")
        String destination,
        @FutureOrPresent(message = "Date field can't be set in the past")
        LocalDate date,
        @NotBlank(message = "State field can't be null or blank")
        @Size(min = 3, message = "State field has to be at least 3 characters long")
        String state
) {
}
