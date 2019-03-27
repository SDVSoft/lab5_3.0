public class Door extends Item implements PossibleToDrill, iDoor {
    private static final String DESCRIPTION = "Дверь";
    private enum eCondition {OPEN, CLOSED}
    private eCondition condition;

    Door() {
        super(DESCRIPTION);
        condition = eCondition.OPEN;
    }

    protected Door(String description) {super(description);}

    public Item makeHole() {return new HoledDoor();}

    public void close() {
        condition = eCondition.CLOSED;
    }

    public void open() {
        condition = eCondition.OPEN;
    }

    public boolean isOpen() {
        return (condition == eCondition.OPEN) ? true: false;
    }
}
