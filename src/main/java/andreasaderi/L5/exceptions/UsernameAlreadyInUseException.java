package andreasaderi.L5.exceptions;

public class UsernameAlreadyInUseException extends RuntimeException {
    public UsernameAlreadyInUseException(String username) {
        super("Username " + username + " has already been taken.");
    }
}
