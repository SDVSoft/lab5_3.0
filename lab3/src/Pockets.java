public class Pockets extends ItemContainer implements LootBag {
    private static final String DESCRIPTION = "Карманы";
    private static final int CAPACITY = 6;

    Pockets() {super(DESCRIPTION, CAPACITY);}

    protected Pockets(String description, int capacity) {super(description, capacity);}
}
