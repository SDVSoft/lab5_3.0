package exceptions;

import javax.management.openmbean.KeyAlreadyExistsException;

/**
 * An OrganizationAlreadyExistsException is thrown when an attempt is made to create
 * an Organization that already exists or when some other Organization has the same name
 * with new one.
 *
 */
public class OrganizationAlreadyExistsException extends KeyAlreadyExistsException {
    /*
    KeyAlreadyExistsException extends RuntimeException --- непроверяемое исключение. Ловить не обязательно.
     */

    /**
     * Constructs an OrganizationAlreadyExistsException with no specified detail message.
     */
    public OrganizationAlreadyExistsException() {super("Эта организация, или организация с таким же именем уже существует.");}

    /**
     * Constructs an OrganizationAlreadyExistsException with the specified detail message.
     * @param message - the detail message.
     */
    public OrganizationAlreadyExistsException(String message){super(message);}
}
