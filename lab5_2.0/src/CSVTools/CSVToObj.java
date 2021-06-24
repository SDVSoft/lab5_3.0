package CSVTools;

import Exceptions.ObjectCreationFailedException;

/**
 * A CSVToObj is a parser that provides creating objects of specified type T from CSVRows
 * @param <T> - type of objects to be created
 */
public interface CSVToObj<T> {
    T createFromCSV(CSVRow csvRow) throws ObjectCreationFailedException;
}
