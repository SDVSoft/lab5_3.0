package CSVTools;

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
        csvRow = "";
        for (String value : values) {
            if (!csvRow.equals("")) csvRow += ",";
            value = value.replace("\"", "\"\"");
            if (value.contains("\"") || value.contains(",") || value.contains("\n"))
                value = "\"" + value + "\"";
            csvRow += value;
        }
    }

    public CSVRow(String csvRow) {
        //TODO: Exception when incorrectly placed quotes
        this.csvRow = csvRow;
        LinkedList<String> valuesQueue = new LinkedList<>();
        String value;
        boolean quotes = false, quoted = false;
        int valueBeginIndex = 0, valueEndIndex = 0;
        csvRow += ",";
        for (int i = 0; i < csvRow.length(); i++) {
            if (quotes) {
                if (csvRow.charAt(i) == '\"') quotes = false;
            }
            else if (csvRow.charAt(i) == '\"') quotes = quoted = true;
            else if (csvRow.charAt(i) == ',') {
                valueEndIndex = i;
                if (quoted) {
                    valueBeginIndex++;
                    valueEndIndex--;
                }
                value = csvRow.substring(valueBeginIndex, valueEndIndex);
                valuesQueue.add(value.replace("\"\"", "\""));
                quoted = false;
                valueBeginIndex = i + 1;
            }
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
