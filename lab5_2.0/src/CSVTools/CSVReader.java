package CSVTools;

import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CSVReader {
    private Scanner sc;

    public CSVReader(Scanner sc) { this.sc = sc; }

    public CSVRow readCSVRow() throws ParseException {
        return readCSVRow(sc);
    }

    public static CSVRow readCSVRow(Scanner sc) throws ParseException {
        StringBuilder str = new StringBuilder();
        try {
            str.append(sc.nextLine());
        } catch (NoSuchElementException nsee) {
            throw new ParseException("Ошибка при чтении CSV: Пустая строка.", 0);
        }
        int nSeparators = 0, charsParsed = str.length();
        boolean strIsComplete = false;
        String addedStr = str.toString();
        while (!strIsComplete) {
            for (char c : addedStr.toCharArray())
                if (c == '"') nSeparators++;
            if (nSeparators % 2 == 0) strIsComplete = true;
            else {
                try {
                    addedStr = "\n" + sc.nextLine();
                } catch (NoSuchElementException nsee) {
                    throw new ParseException("Ошибка при чтении CSV: не закрытые двойные кавычки.", charsParsed);
                }
                str.append(addedStr);
                charsParsed += addedStr.length();
            }
        }
        return new CSVRow(str.toString());
    }
}
