package andreasaderi.L5.exceptions;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeAlreadyHasBookingForThisDateException extends RuntimeException {
    public EmployeeAlreadyHasBookingForThisDateException(UUID employeeId, LocalDate date) {
        super("Employee id " + employeeId + " already has an ongoing booking for date " + date);
    }
}
