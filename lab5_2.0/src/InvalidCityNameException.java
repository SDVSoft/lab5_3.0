public class InvalidCityNameException extends InvalidCityDataException{
    /*
    Exception --- проверяемое исключение. Ловить обязательно.
     */

    public InvalidCityNameException() {super("Недопустимое имя города: имя города не может быть пустой строкой.");}
    public InvalidCityNameException(String message){super(message);}
}