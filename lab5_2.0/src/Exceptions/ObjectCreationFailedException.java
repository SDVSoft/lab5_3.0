package Exceptions;

public class ObjectCreationFailedException extends Exception {
    /*
    Exception --- непроверяемое исключение. Ловить обязательно.
     */

    public ObjectCreationFailedException() {super("Не удалось создать объект.");}
    public ObjectCreationFailedException(String message) {super(message);}
    public ObjectCreationFailedException(String message, Throwable cause) {super(message, cause);}
    public ObjectCreationFailedException(Throwable cause) {super(cause);}
}
