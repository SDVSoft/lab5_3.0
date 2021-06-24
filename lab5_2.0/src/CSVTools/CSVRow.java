package CSVTools;

import Exceptions.InvalidQuoteSequenceException;

import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * The CSVRow class represents an object parsed from CSV/
 */
public class CSVRow {
    private String csvRow;
    private String[] values;
    private int size;
    private static final char DELIMITER = ',';

    /**
     * Creates a new CSVRow from given values
     * @param values - array of values
     */
    public CSVRow(String[] values) {
        this.values = Arrays.copyOf(values, values.length);
        size = values.length;
        StringBuilder str = new StringBuilder();
        for (String value : values) {
            if (str.length() != 0) str.append(DELIMITER);
            value = value.replace("\"", "\"\"");
            if (value.contains("\"") || value.contains(String.valueOf(DELIMITER)) || value.contains("\n"))
                value = "\"" + value + "\"";
            str.append(value);
        }
        csvRow = str.toString();
    }

    /**
     * Parse given String into a new CSVRow
     * @param csvRow - complete CSV line
     */
    public CSVRow(String csvRow) throws InvalidQuoteSequenceException {
        //Added: Exception when incorrectly placed quotes
        this.csvRow = csvRow;
        LinkedList<String> valuesQueue = new LinkedList<>();
        String value;
        boolean quotes = false, quoted = false;
        int valueBeginIndex = 0, valueEndIndex;
        csvRow = csvRow + DELIMITER;
        for (int i = 0; i < csvRow.length(); i++) {
            if (quotes) {
                if (csvRow.charAt(i) == '"') quotes = false;
            } else if (csvRow.charAt(i) == '"') {
                if (i == 0 || csvRow.charAt(i - 1) == DELIMITER || quoted)
                    quotes = quoted = true;
                else throw new InvalidQuoteSequenceException("Невозможно корректно обработать данные: " +
                        "CSV-поле, содержащее кавычки, не заключено в кавычки.", i);
            } else if (csvRow.charAt(i) == DELIMITER) {
                valueEndIndex = i;
                if (quoted) {
                    if (csvRow.charAt(i - 1) != '"')
                        throw new InvalidQuoteSequenceException("Невозможно корректно обработать данные: " +
                                "CSV-поле, содержащее кавычки, не заключено в кавычки.", i);
                    valueBeginIndex++;
                    valueEndIndex--;
                }
                value = csvRow.substring(valueBeginIndex, valueEndIndex);
                valuesQueue.add(value.replace("\"\"", "\""));
                quoted = false;
                valueBeginIndex = i + 1;
            } else if (quoted) {
                throw new InvalidQuoteSequenceException("Невозможно корректно обработать данные: " +
                        "символ кавычки в CSV-поле не удвоен.", i - 1);
            }
        }
        size = valuesQueue.size();
        values = valuesQueue.toArray(new String[0]);
    }

    /**
     * Returns delimiter using to separate values. (',')
     * @return delimiter
     */
    public static char delimiter() { return DELIMITER; }

    /**
     * Returns an amount of values in this row
     * @return amount of values
     */
    public int size() { return size; }

    /**
     * Returns an array representation of this CSVRow
     * @return array of values
     */
    public String[] getValues() { return Arrays.copyOf(values, size); }

    /**
     * Returns CSV representation of this CSVRow
     * @return String in CSV format
     */
    public String toString() { return csvRow; }
/*
    public static void main(String[] args) throws ParseException {
        CSVReader csvReader = new CSVReader(System.in);
        CSVRow csvRow = csvReader.readCSVRow();
        while(!csvRow.toString().equals("stop")) {
            for (int i = 0; i < csvRow.size; i++)
                System.out.print("|" + csvRow.values[i] + "|");
            System.out.println();
            csvRow = csvReader.readCSVRow();
        }
    }*/
}
