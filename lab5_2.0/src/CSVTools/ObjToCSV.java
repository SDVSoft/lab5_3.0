package CSVTools;

/**
 * An ObjToCSV is a serializer that provides serialization for specified type of objects
 * into CSVRow
 * @param <T> - a type of objects to be serialized
 */
public interface ObjToCSV<T> {
    CSVRow serializeToCSV(T t);
}
