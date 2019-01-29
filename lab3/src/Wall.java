public class Wall extends ItemContainer {
    private static final String DESCRIPTION = "Стена";
    private static final int CAPACITY = 100;

    Wall() {super(DESCRIPTION, CAPACITY);}

    protected Wall(String description, int capacity) {super(description, capacity);}
}