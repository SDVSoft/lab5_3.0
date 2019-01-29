public class PostBox extends ItemContainer {
    private static final String DESCRIPTION = "Почтовый ящик";
    private static final int CAPACITY = 45;

    PostBox() {super(DESCRIPTION, CAPACITY);}

    protected PostBox(String description, int capacity) {super(description, capacity);}

    public boolean putItem(Item it) {
        if (it instanceof Writing) {
            return super.putItem(it);
        }
        System.out.println("Невозможно положить \"" + it + "\" в \"" + this + "\".");
        return false;
    }

    public Writing[] receivePost() {
        Writing[] post = new Writing[super.getSize()];
        String[] info = getContentInfo();
        for (int i = 0; i < post.length; i++) {
            post[i] = (Writing) getItem(info[i]);
        }
        System.out.println("Получено " + post.length + " писем.");
        return post;
    }
}
