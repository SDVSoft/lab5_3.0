public interface iItemContainer {

    int getCapacity();
    int getSize();
    boolean isFull();
    boolean putItem(Item it);
    Item getItem(String item_descr);
    String[] getContentInfo();
}
