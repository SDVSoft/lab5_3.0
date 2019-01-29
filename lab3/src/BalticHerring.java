public class BalticHerring extends Item implements Food {
    private boolean cooked;
    private static final String DESCRIPTION = "Салака";

    BalticHerring() {
        super(DESCRIPTION);
        cooked = false;
    }

    protected BalticHerring(String description) {super(description);}

    public void cook() {cooked = true;}

    public boolean isCooked() {return cooked;}
}
