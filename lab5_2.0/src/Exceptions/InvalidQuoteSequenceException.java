package Exceptions;

import java.text.ParseException;

public class InvalidQuoteSequenceException extends ParseException {
    /*
    ParseException extends Exception --- непроверяемое исключение. Ловить обязательно.
     */
    public InvalidQuoteSequenceException(String message, int errorOffset) {
        super(message, errorOffset);
    }

    public InvalidQuoteSequenceException(int errorOffset) {
        super("Обнаружена некорректная последовательность кавычек.", errorOffset);
    }
}
