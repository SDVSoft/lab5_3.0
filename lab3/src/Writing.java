import java.util.Arrays;

public abstract class Writing extends Item implements iWriting {
    private String[] text;
    private final int CAPACITY;
    private int size;

    protected Writing(String description, int capacity) {
        super(description);
        CAPACITY = capacity;
        size = 0;
        text = new String[capacity];
    }

    protected Writing(String description, int capacity, String[] text) {
        this(description, capacity);
        for (int i = 0; i < capacity && i < text.length; i++)
            this.text[i] = text[i];
        size = Math.min(capacity, text.length);
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && Arrays.equals(((Writing) obj).text, text);
    }

    public int hashCode() {
        if (size == 0) return super.hashCode();
        return text[0].hashCode();
    }

    public int getCapacity() {return CAPACITY;}

    public int getSize() {return size;}

    public String[] getText() {return getText(size);}

    public String[] getText(int strings) {
        strings = Math.min(size, strings);
        String[] res = new String[strings];
        for (int i = 0; i < strings; i++)
            res[i] = text[i];
        return res;
    }

    public int writeText(String[] t) {
        size = Math.min(t.length, CAPACITY);
        for (int i = 0; i < size; i++)
            text[i] = t[i];
        return size;
    }

    public int addText(String[] t) {
        int new_size = size + Math.min(t.length, CAPACITY);
        for (int i = size, j = 0; i < new_size; i++, j++)
            text[i] = t[j];
        return size = new_size;
    }
}
