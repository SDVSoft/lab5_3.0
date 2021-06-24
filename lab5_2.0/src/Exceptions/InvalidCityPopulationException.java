package Exceptions;

/**
 * An InvalidCityPopulationException is thrown when a value that is not a legal city
 * population is used as such.
 */
public class InvalidCityPopulationException extends InvalidCityDataException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    /**
     * Constructs an InvalidCityPopulationException with no specified detail message.
     */
    public InvalidCityPopulationException() {super("Недопустимое значение населения города: население города должно быть больше 0.");}

    /**
     * Constructs an InvalidCityPopulationException with the specified detail message.
     * @param message - the detail message.
     */
    public InvalidCityPopulationException(String message){super(message);}
}
