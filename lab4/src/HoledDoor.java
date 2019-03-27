public class HoledDoor extends Door {
    private static final String DESCRIPTION = "Дырявая дверь";
    private int holes;

    HoledDoor() {
        super(DESCRIPTION);
        holes = 1;
    }

    protected HoledDoor(String description) {super(description);}

    public int getHoles() {return holes;}

    public Item makeHole() {
        holes++;
        return this;
    }
}
