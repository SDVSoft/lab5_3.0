package CSVTools;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A CSVReader is a class for reading CSVRows from Scanner or InputStream.
 */
public class CSVReader {
    private Scanner sc;
    private BufferedInputStream bufferedIS;
    private Charset charset;
    private byte[][] lineSeparatorsBytes;
    private byte[] lfBytes;
    private static final String[] LINE_SEPARATORS = "\n\r\u2028\u2029\u0085".split("");
    private String lastLineSeparator;

    /**
     * Creates a CSVReader that uses the given InputStream
     * @param is - InputStream to read from
     */
    public CSVReader(InputStream is) { this(is, Charset.defaultCharset()); }

    /**
     * Creates a CSVReader that uses the given InputStream and the specified charset
     * @param is - an InputStream to read from
     * @param charsetName - a name of the Charset to use
     * @throws UnsupportedCharsetException If no support for the named charset is
     * available in this instance of the Java virtual machine
     */
    public CSVReader(InputStream is, String charsetName) throws UnsupportedCharsetException {
        this(is, Charset.forName(charsetName));
    }

    /**
     * Creates a CSVReader that uses the given InputStream and the specified charset
     * @param is - an InputStream to read from
     * @param charset - a Charset to use
     */
    public CSVReader(InputStream is, Charset charset) {
        this.bufferedIS = new BufferedInputStream(is);
        this.charset = charset;
        lineSeparatorsBytes = new byte[LINE_SEPARATORS.length][];
        for (int i = 0; i < LINE_SEPARATORS.length; i++)
            lineSeparatorsBytes[i] = LINE_SEPARATORS[i].getBytes(charset);
        lfBytes = "\n".getBytes(charset);
    }

    /**
     * Creates a CSVReader that uses the given Scanner
     * @param sc - a Scanner to read from
     */
    public CSVReader(Scanner sc) { this.sc = sc; }

    /**
     * Returns true if CSVReader has anything else to read. This method may block while
     * waiting for input to scan if you use Scanner as a source.
     * @return true if and only if this CSVReader has anything else to read
     */
    public boolean hasNext() {
        if (sc != null)
            return sc.hasNext();
        bufferedIS.mark(1);
        try {
            int b = bufferedIS.read();
            bufferedIS.reset();
            return b != -1;
        } catch (IOException ioe) {
            return false;
        }
    }

    private String readLineFromIS() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int b, prevB;
            boolean endOfLine = false;
            while (!endOfLine && (b = bufferedIS.read()) != -1) {
                for (byte[] bytesOfLineSep : lineSeparatorsBytes) {
                    if (b == bytesOfLineSep[0]) {
                        bufferedIS.mark(4);
                        prevB = b;
                        endOfLine = true;
                        for (int i = 1; i < bytesOfLineSep.length && endOfLine; i++) {
                            if ((b = bufferedIS.read()) != bytesOfLineSep[i])
                                endOfLine = false;
                        }
                        if (endOfLine) {
                            lastLineSeparator = new String(bytesOfLineSep, charset);
                            if (lastLineSeparator.equals("\r")) {
                                boolean crlfLineSep = true;
                                bufferedIS.mark(4);
                                for (int i = 0; i < lfBytes.length && crlfLineSep; i++) {
                                    if ((b = bufferedIS.read()) != lfBytes[i])
                                        crlfLineSep = false;
                                }
                                if (crlfLineSep) lastLineSeparator = "\r\n";
                                else bufferedIS.reset();
                            }
                            break;
                        } else {
                            b = prevB;
                            bufferedIS.reset();
                        }
                    }
                }
                if (!endOfLine)
                    out.write(b);
            }
            return out.toString(charset.name());
        }
    }

    private String readLine() throws IOException, NoSuchElementException {
        if (sc == null)
            return readLineFromIS();
        String result = sc.nextLine();
        lastLineSeparator = sc.match().group(1);
        return result;
    }

    /**
     * Reads complete CSVRow from source.
     * @return CSVRow that has been read
     * @throws ParseException if any errors occurs
     */
    public CSVRow readCSVRow() throws ParseException {
        StringBuilder str = new StringBuilder();
        try {
            str.append(readLine());
        } catch (UnsupportedEncodingException uee) {
            ParseException pe = new ParseException("Ошибка при чтении CSV: указана неподдерживаемая кодировка.", 0);
            pe.initCause(uee);
            throw pe;
        } catch (IOException | NoSuchElementException e) {
            ParseException pe = new ParseException("Ошибка при чтении CSV: пустая строка.", 0);
            pe.initCause(e);
            throw pe;
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
                    if (!hasNext()) throw new NoSuchElementException("No line found");
                    addedStr = lastLineSeparator + readLine();
                } catch (IOException | NoSuchElementException e) {
                    ParseException pe = new ParseException("Ошибка при чтении CSV: незакрытые двойные кавычки.", charsParsed);
                    pe.initCause(e);
                    throw pe;
                }
                str.append(addedStr);
                charsParsed += addedStr.length();
            }
        }
        return new CSVRow(str.toString());
    }
}
