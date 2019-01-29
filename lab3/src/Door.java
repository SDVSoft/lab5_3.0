public class Door extends Item implements PossibleToDrill {
    private static final String DESCRIPTION = "Дверь";

    Door() {super(DESCRIPTION);}

    protected Door(String description) {super(description);}

    public Item makeHole() {return new HoledDoor();}
}
