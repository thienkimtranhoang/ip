package mimon;

/**
 * A custom exception class for handling errors specific to the Mimon task management system.
 * Extends the standard Exception class to provide more specific error handling.
 */
public class MimonException extends Exception {
    public MimonException(String message) {
        super(message);
    }
}