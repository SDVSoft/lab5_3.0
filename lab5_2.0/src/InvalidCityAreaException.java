public class InvalidCityAreaException extends InvalidCityDataException {
    /*
    Exception --- проверяемое исключение. Ловить обязательно.
     */

    public InvalidCityAreaException() {super("Недопустимое значение площади города: площадь города должна быть больше 0.");}
    public InvalidCityAreaException(String message){super(message);}
}
