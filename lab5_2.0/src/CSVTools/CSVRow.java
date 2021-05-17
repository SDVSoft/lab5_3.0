package CSVTools;

import Exceptions.InvalidQuoteSequenceException;

import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class CSVRow {
    private String csvRow;
    private String[] values;
    private int size;

    public CSVRow(String[] values) {
        this.values = values;
        size = values.length;
        StringBuilder str = new StringBuilder();
        for (String value : values) {
            if (str.length() != 0) str.append(",");
            value = value.replace("\"", "\"\"");
            if (value.contains("\"") || value.contains(",") || value.contains("\n"))
                value = "\"" + value + "\"";
            str.append(value);
        }
        csvRow = str.toString();
    }

    public CSVRow(String csvRow) throws InvalidQuoteSequenceException {
        //Added: Exception when incorrectly placed quotes
        this.csvRow = csvRow;
        LinkedList<String> valuesQueue = new LinkedList<>();
        String value;
        boolean quotes = false, quoted = false;
        int valueBeginIndex = 0, valueEndIndex;
        csvRow += ",";
        for (int i = 0; i < csvRow.length(); i++) {
            if (quotes) {
                if (csvRow.charAt(i) == '\"') quotes = false;
            } else if (csvRow.charAt(i) == '\"') {
                if (i == 0 || csvRow.charAt(i - 1) == ',' || quoted)
                    quotes = quoted = true;
                else throw new InvalidQuoteSequenceException("Невозможно корректно обработать данные: " +
                        "CSV-поле, содержащее кавычки, не заключено в кавычки.", i);
            } else if (csvRow.charAt(i) == ',') {
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
            } else if (quoted)
                throw new InvalidQuoteSequenceException("Невозможно корректно обработать данные: " +
                        "символ кавычки в CSV-поле не удвоен.", i - 1);
        }
        size = valuesQueue.size();
        values = new String[size];
        for (int i = 0; i < size; i++)
            values[i] = valuesQueue.poll();
    }

    public int getSize() { return size; }

    public String[] getValues() { return Arrays.copyOf(values, size); }

    public String toString() { return csvRow; }

    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        CSVReader csvReader = new CSVReader(sc);
        CSVRow csvRow = csvReader.readCSVRow();
        while(!csvRow.toString().equals("stop")) {
            for (int i = 0; i < csvRow.size; i++)
                System.out.print("|" + csvRow.values[i] + "|");
            System.out.println();
            csvRow = csvReader.readCSVRow();
        }
     }
}
