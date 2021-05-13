package Exceptions;

import Exceptions.InvalidCityDataException;

public class InvalidCityPopulationException extends InvalidCityDataException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    public InvalidCityPopulationException() {super("Недопустимое значение населения города: население города должно быть больше 0.");}
    public InvalidCityPopulationException(String message){super(message);}
}
