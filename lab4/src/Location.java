import java.util.ArrayList;

public enum Location implements iItemContainer {
    OUTDOOR(),
    DOCTOR(),
    MARKET(),
    KID_ROOM(new Wall(), new Door()),
    HOUSE_ON_THE_ROOF(new Wall(), new Door()),
    ENTRANCE_HALL(new Wall(), new Door()),
    LIVING_ROOM(new Wall(), new Door());

    private ArrayList<Item> items;
    private ArrayList<Human> humans;
    private Wall wall;
    private iDoor door;

    Location() {
        items = new ArrayList<Item>();
        humans = new ArrayList<Human>();
    }

    Location(Wall wall, iDoor door) {
        this();
        this.wall = wall;
        this.door = door;
    }

    public boolean hasWall() {return wall != null;}

    public boolean hasDoor() {return door != null;}

    public boolean isDoorOpen() {return hasDoor() && door.isOpen();}

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

    public String[] getWallContentInfo() {
        if (hasWall()) return wall.getContentInfo();
        return new String[0];
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
        if (hasWall() && wall.equals(container_info)) {
            gotten = wall.getItem(item_info);
            if (gotten != null) return gotten;
        }
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
        if (hasWall() && wall.equals(container_info))
            return wall.putItem(it);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(container_info)) {
                return ((iItemContainer)items.get(i)).putItem(it);
            }
        }
        return false;
    }

    public void enter(Human h) {humans.add(h);}

    public void exit(Human h) {humans.remove(h);}

    public static void main(String[] args) {
        System.out.println((KID_ROOM.hasWall())? "t" : "f");
        System.out.println((OUTDOOR.hasWall())? "t" : "f");
    }

    public Item removeDoor() {
        if (hasDoor()) {
            Item gotten = (Item) door;
            door = null;
            return gotten;
        }
        return null;
    }

    public void setDoor(iDoor it) {
        if (hasDoor()) putItem((Item) door);
        door = it;
    }

    public void openDoor() {
        if (hasDoor()) door.open();
    }

    public void closeDoor() {
        if (hasDoor()) door.close();
    }
}