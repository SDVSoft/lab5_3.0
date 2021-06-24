package Exceptions;

/**
 * An InvalidCityDataException is thrown when a value that is not a legal city data
 * is used as such.
 */
public class InvalidCityDataException extends IllegalArgumentException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    /**
     * Constructs an InvalidCityDataException with no specified detail message.
     */
    public InvalidCityDataException() {super("Недопустимое значение параметра города.");}

    /**
     * Constructs an InvalidCityDataException with the specified detail message.
     * @param message - the detail message.
     */
    public InvalidCityDataException(String message){super(message);}
}
