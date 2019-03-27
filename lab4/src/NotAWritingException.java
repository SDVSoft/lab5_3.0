public class NotAWritingException extends Exception {
    /*
    RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    public NotAWritingException() {
        super("Ты чё, бредишь? Такое не читается. Минералочки хлебни.");
    }
    public NotAWritingException(String message) {super(message);}
}