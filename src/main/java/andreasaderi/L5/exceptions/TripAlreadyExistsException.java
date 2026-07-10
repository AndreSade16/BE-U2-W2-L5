package andreasaderi.L5.exceptions;

import java.time.LocalDate;

public class TripAlreadyExistsException extends RuntimeException {
    public TripAlreadyExistsException(String destination, LocalDate date) {
        super("Trip with destination " + destination + " set in date " + date + " already exists.");
    }
}
