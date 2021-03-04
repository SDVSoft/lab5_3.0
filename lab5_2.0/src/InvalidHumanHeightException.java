public class InvalidHumanHeightException extends Exception {
    /*
    Exception --- проверяемое исключение. Ловить обязательно.
     */

    public InvalidHumanHeightException() {super("Недопустимое значение высоты человека: высота человека должна быть больше 0.");}
    public InvalidHumanHeightException(String message){super(message);}
}
