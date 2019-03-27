public abstract class Item {
    private final String DESCRIPTION;

    Item(String description) {this.DESCRIPTION = description;}

    public final String getDescription() {return DESCRIPTION;}

    public String toString() {return DESCRIPTION;}

    public boolean equals(String s) {return toString().equals(s);}

    public boolean equals(Object obj) {
        if (obj instanceof Item)
            return equals(((Item)obj).DESCRIPTION);
        return false;
    }

    public int hashCode() {return DESCRIPTION.hashCode();}
}
