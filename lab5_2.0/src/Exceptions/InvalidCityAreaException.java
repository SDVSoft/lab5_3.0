package Exceptions;

/**
 * An InvalidCityAreaException is thrown when a value that is not a legal city area
 * is used as such.
 */
public class InvalidCityAreaException extends InvalidCityDataException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    /**
     * Constructs an InvalidCityAreaException with no specified detail message.
     */
    public InvalidCityAreaException() {super("Недопустимое значение площади города: площадь города должна быть больше 0.");}

    /**
     * Constructs an InvalidCityAreaException with the specified detail message.
     * @param message - the detail message.
     */
    public InvalidCityAreaException(String message){super(message);}
}
