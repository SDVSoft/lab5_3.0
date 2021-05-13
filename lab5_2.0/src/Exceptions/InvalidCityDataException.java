package Exceptions;

public class InvalidCityDataException extends IllegalArgumentException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    public InvalidCityDataException() {super("Недопустимое значение параметра города.");}
    public InvalidCityDataException(String message){super(message);}
}
