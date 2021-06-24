package Exceptions;

/**
 * An InvalidCityNameException is thrown when a value that is not a legal city name is
 * used as such.
 */

public class InvalidCityNameException extends InvalidCityDataException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    /**
     * Constructs an InvalidCityNameException with no specified detail message.
     */
    public InvalidCityNameException() {super("Недопустимое имя города: имя города не может быть пустой строкой.");}

    /**
     * Constructs an InvalidCityNameException with the specified detail message.
     * @param message - the detail message.
     */
    public InvalidCityNameException(String message){super(message);}
}
