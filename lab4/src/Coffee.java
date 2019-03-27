public class Coffee extends Item implements Food {
    private boolean cooked;
    private static final String DESCRIPTION = "Кофе";

    Coffee() {
        super(DESCRIPTION);
        cooked = false;
    }

    protected Coffee(String description) {super(description);}

    public void cook() {cooked = true;}

    public boolean isCooked() {return cooked;}
}
