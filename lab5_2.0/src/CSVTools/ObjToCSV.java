package CSVTools;

public interface ObjToCSV<T> {
    CSVRow serializeToCSV(T t);
}
