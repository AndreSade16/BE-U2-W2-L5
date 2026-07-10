package andreasaderi.L5.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException(String email) {
        super("Email " + email + " has already been used.");
    }
}
