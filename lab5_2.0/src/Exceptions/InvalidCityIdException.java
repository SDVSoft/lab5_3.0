package Exceptions;

public class InvalidCityIdException extends InvalidCityDataException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    public InvalidCityIdException() {super("Недопустимое значение id города: id должен быть больше 0.");}
    public InvalidCityIdException(String message){super(message);}
}
