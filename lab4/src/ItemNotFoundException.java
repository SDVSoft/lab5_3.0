public class ItemNotFoundException extends RuntimeException {
    /*
    Exception --- проверяемое исключение. Ловить обязательно.
     */

    public ItemNotFoundException() {super("Такого предмета здесь нет!");}
    public ItemNotFoundException(String message){super(message);}
}
