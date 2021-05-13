package Exceptions;

import Exceptions.InvalidCityDataException;

public class InvalidCityNameException extends InvalidCityDataException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    public InvalidCityNameException() {super("Недопустимое имя города: имя города не может быть пустой строкой.");}
    public InvalidCityNameException(String message){super(message);}
}
