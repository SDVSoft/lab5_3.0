public class InvalidCityDataException extends Exception {
    /*
    Exception --- проверяемое исключение. Ловить обязательно.
     */

    public InvalidCityDataException() {super("Недопустимое значение параметра города.");}
    public InvalidCityDataException(String message){super(message);}
}
