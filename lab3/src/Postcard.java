public final class Postcard extends Writing {
    private static final String DESCRIPTION = "Открытка";
    private static final int CAPACITY = 1;
    private String sender;

    Postcard(String sender) {
        super(DESCRIPTION, CAPACITY);
        this.sender = sender;
    }

    Postcard(String sender, String[] text) {
        super(DESCRIPTION, CAPACITY, text);
        this.sender = sender;
    }

    Postcard(String sender, String text) {
        this(sender, new String[]{text});
    }

    public String toString() {
        return super.toString() + " от " + sender;
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && ((Postcard)obj).sender.equals(sender);
    }

    public boolean equals(String s) {return toString().equals(s);}
}
