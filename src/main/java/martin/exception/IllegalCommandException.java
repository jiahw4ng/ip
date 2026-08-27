package martin.exception;

/**
 * Represents an exception thrown when an unrecognized or invalid command is
 * encountered.
 */
public class IllegalCommandException extends RuntimeException {

    /**
     * Constructs an {@code IllegalCommandException} with the specified detail
     * message.
     *
     * @param message the detail error message
     */
    public IllegalCommandException(String message) {
        super(message);
    }

}
