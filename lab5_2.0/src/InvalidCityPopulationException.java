public class InvalidCityPopulationException extends InvalidCityDataException {
    /*
    Exception --- проверяемое исключение. Ловить обязательно.
     */

    public InvalidCityPopulationException() {super("Недопустимое значение населения города: население города должно быть больше 0.");}
    public InvalidCityPopulationException(String message){super(message);}
}
