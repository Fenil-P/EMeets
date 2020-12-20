package Exceptions;

/**
 * Throws this error whenever there is an issue with an object that does not exist in our system.
 *
 * @version 1.1
 */

public class DoesNotExistError extends Exception {
    public DoesNotExistError(String message) {
        super(message);
    }
}
