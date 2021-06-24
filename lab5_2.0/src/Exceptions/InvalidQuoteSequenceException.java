package Exceptions;

import java.text.ParseException;

/**
 * An InvalidQuoteSequenceException is thrown if an attempt is made to parse a CSVRow with
 * wrong order of quotes.
 */
public class InvalidQuoteSequenceException extends ParseException {
    /*
    ParseException extends Exception --- непроверяемое исключение. Ловить обязательно.
     */

    /**
     * Constructs a new InvalidQuoteSequenceException with the specified detail message
     * and offset. A detail message is a String that describes this particular exception.
     * @param message - the detail message
     * @param errorOffset - the position where the error is found while parsing.
     */
    public InvalidQuoteSequenceException(String message, int errorOffset) {
        super(message, errorOffset);
    }

    /**
     * Constructs an AlreadyBoundException with no specified detail message.
     * @param errorOffset - the position where the error is found while parsing.
     */
    public InvalidQuoteSequenceException(int errorOffset) {
        super("Обнаружена некорректная последовательность кавычек.", errorOffset);
    }
}
