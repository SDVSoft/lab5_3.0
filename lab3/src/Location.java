import java.util.ArrayList;

public enum Location implements iItemContainer {
    OUTDOOR(),
    DOCTOR(),
    MARKET(),
    KID_ROOM(),
    HOUSE_ON_THE_ROOF(),
    ENTRANCE_HALL(),
    LIVING_ROOM();

    private ArrayList<Item> items;
    private ArrayList<Human> humans;

    Location() {
        items = new ArrayList<Item>();
        humans = new ArrayList<Human>();
    }

    public int getCapacity() {return Integer.MAX_VALUE;}

    public int getSize() {return items.size();}

    public boolean isFull() {return false;}

    public String[] getContentInfo() {
        String[] info = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            info[i] = items.get(i).toString();
        }
        return info;
    }

    public Item getItem(String item_info) {
        for (int i = 0; i < items.size(); i++ ) {
            if (items.get(i).equals(item_info))
                return items.remove(i);
        }
        return null;
    }

    public Item getItem(String item_info, String container_info) {
        Item gotten;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(container_info)) {
                gotten = ((ItemContainer) items.get(i)).getItem(item_info);
                if (gotten != null) return gotten;
            }
        }
        return null;
    }

    public boolean putItem(Item it) {
        items.add(it);
        return true;
    }

    public boolean putItem(Item it, String container_info) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(container_info)) {
                return ((iItemContainer)items.get(i)).putItem(it);
            }
        }
        return false;
    }

    public void enter(Human h) {humans.add(h);}

    public void exit(Human h) {humans.remove(h);}
}