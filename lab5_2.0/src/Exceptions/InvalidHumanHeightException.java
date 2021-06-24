package Exceptions;

/**
 * An InvalidHumanHeightException is thrown when a value that is not a legal human height
 * is used as such.
 */
public class InvalidHumanHeightException extends IllegalArgumentException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    /**
     * Constructs an InvalidHumanHeightException with no specified detail message.
     */
    public InvalidHumanHeightException() {super("Недопустимое значение высоты человека: высота человека должна быть больше 0.");}

    /**
     * Constructs an InvalidHumanHeightException with the specified detail message.
     * @param message - the detail message.
     */
    public InvalidHumanHeightException(String message){super(message);}
}
