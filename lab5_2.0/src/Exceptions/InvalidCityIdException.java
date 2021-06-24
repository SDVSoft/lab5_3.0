package Exceptions;

/**
 * An InvalidCityIdException is thrown when a value that is not a legal city id is used as
 * such.
 */
public class InvalidCityIdException extends InvalidCityDataException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    /**
     * Constructs an InvalidCityIdException with no specified detail message.
     */
    public InvalidCityIdException() {super("Недопустимое значение id города: id должен быть больше 0.");}

    /**
     * Constructs an InvalidCityIdException with the specified detail message.
     * @param message - the detail message.
     */
    public InvalidCityIdException(String message){super(message);}
}
