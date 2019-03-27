public class ItemContainer extends Item implements iItemContainer {
    private static final String DESCRIPTION = "Контейнер";
    private static final int CAPACITY = 100;
    private Item[] items;
    private int num_items;

    protected ItemContainer(String description, int capacity) {
        super(description);
        items = new Item[capacity];
    }

    public ItemContainer() {this(DESCRIPTION, CAPACITY);}

    public int getCapacity() {return items.length;}

    public int getSize() {return num_items;}

    public boolean isFull() {return num_items == items.length;}

    public int hashCode() {
        if (num_items == 0) return super.hashCode();
        return (DESCRIPTION + items[0].getDescription()).hashCode();
    }

    public String[] getContentInfo() {
        String[] info = new String[num_items];
        for (int i = 0; i < num_items; i++) {
            info[i] = items[i].toString();
        }
        return info;
    }

    public boolean putItem(Item it) {
        if (!isFull()) {
            items[num_items++] = it;
            return true;
        }
        System.out.println("Не удалось положить \"" + it + "\" в \"" +
                           this + "\": \"" + this + "\" заполнен.");
        return false;
    }

    public Item getItem(String item_info) {
        for (int i = 0; i < num_items; i++) {
            if (items[i].equals(item_info)) {
                Item res = items[i];
                for (int j = i; j < num_items - 1; j++)
                    items[j] = items[j + 1];
                num_items--;
                return res;
            }
        }
        return null;
    }
}