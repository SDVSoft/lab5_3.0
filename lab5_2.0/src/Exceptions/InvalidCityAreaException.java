package Exceptions;

public class InvalidCityAreaException extends InvalidCityDataException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    public InvalidCityAreaException() {super("Недопустимое значение площади города: площадь города должна быть больше 0.");}
    public InvalidCityAreaException(String message){super(message);}
}
