package Exceptions;

public class InvalidHumanHeightException extends IllegalArgumentException {
    /*
    IllegalArgumentException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    public InvalidHumanHeightException() {super("Недопустимое значение высоты человека: высота человека должна быть больше 0.");}
    public InvalidHumanHeightException(String message){super(message);}
}
