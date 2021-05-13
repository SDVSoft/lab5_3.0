package CSVTools;

import Exceptions.ObjectCreationFailedException;

public interface CSVToObj<T> {
    T createFromCSV(CSVRow csvRow) throws ObjectCreationFailedException;
}
