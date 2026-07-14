package andreasaderi.L5.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BookingSearchDTO(@NotNull(message = "TripID field can't be null or blank.")
                               UUID tripId,
                               @NotNull(message = "EmployeeID field can't be null or blank.")
                               UUID employeeId,
                               @NotNull(message = "Notes field can be empty, but not null")
                               String notes) {
}
