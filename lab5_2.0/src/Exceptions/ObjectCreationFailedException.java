package Exceptions;

/**
 * An ObjectCreationFailedException is thrown if an attempt is made to create an object
 * but it is impossible for some reason.
 */
public class ObjectCreationFailedException extends Exception {
    /*
    Exception --- непроверяемое исключение. Ловить обязательно.
     */

    /**
     * Constructs a new ObjectCreationFailedException with no specified detail message.
     */
    public ObjectCreationFailedException() {super("Не удалось создать объект.");}

    /**
     * Constructs an ObjectCreationFailedException with the specified detail message.
     * @param message - the detail message
     */
    public ObjectCreationFailedException(String message) {super(message);}

    /**
     * Constructs a new ObjectCreationFailedException with the specified detail message
     * and cause.
     * @param message - the detail message
     * @param cause - the cause
     */
    public ObjectCreationFailedException(String message, Throwable cause) {super(message, cause);}

    /**
     * Constructs a new ObjectCreationFailedException with the specified cause and a
     * detail message of (cause==null ? null : cause.toString()) (which typically contains
     * the class and detail message of cause).
     * @param cause - the cause (which is saved for later retrieval by the
     *              Throwable.getCause() method). (A null value is permitted, and
     *              indicates that the cause is nonexistent or unknown.)
     */
    public ObjectCreationFailedException(Throwable cause) {super(cause);}
}
