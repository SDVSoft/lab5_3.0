public class Human {
    private String name;
    private Sex sex;
    private Location cur_loc;
    private LootBag inventory = new Pockets() ;

    public Human(String name, Sex sex, Location spawn_point) {
        this.name = name;
        this.sex = sex;
        cur_loc = spawn_point;
    }

    public String toString() {return name;}

    public int hashCode() {return name.hashCode();}

    public Sex getSex() {return sex;}

    public Location getLocation() {return cur_loc;}

    public String[] getInventoryContentInfo() {return inventory.getContentInfo();}

    private Item getItem(String item_info) {
        Item it = inventory.getItem(item_info);
        if (it == null)
            System.out.println(name + " не имеет \"" + item_info +
                               "\" в своём инвентаре.");
        return it;
    }

    protected void changeLoc(Location dest) {
        cur_loc.exit(this);
        dest.enter(this);
        cur_loc = dest;
    }

    protected void printChangeLoc(String mov_act, Location dest) {
        System.out.println(name + " " + mov_act + " в новую локацию: \"" + dest + "\".");
    }

    public void move(Location dest) {
        changeLoc(dest);
        printChangeLoc((sex == Sex.MALE)? "перешёл"  : "перешла", dest);
    }

    public void run() {System.out.println(name + " носится, как угорел" +
                                          ((sex == Sex.MALE)? "ый"  : "ая") + ".");}

    public void run(Location dest) {
        changeLoc(dest);
        printChangeLoc((sex == Sex.MALE)? "побежал"  : "побежала", dest);
    }

    protected void printFailure() {System.out.print(name + " не смог" +
                                                    ((sex == Sex.FEMALE)? "ла": ""));}

    public boolean possibleToTake(String item_info) {
        if (inventory.isFull()) {
            printFailure();
            System.out.print(" взять \"" + item_info);
            System.out.println("\", так как заполнил" +
                               ((sex == Sex.FEMALE)? "а": "") +
                               " свой инвентарь.");
            return false;
        }
        return true;
    }

    public boolean takeItem(Item it) {
        if (possibleToTake(it.toString()))
            return inventory.putItem(it);
        return false;
    }

    public boolean takeItemFromLoc(String item_info) {
        if (!possibleToTake(item_info)) return false;
        Item it = cur_loc.getItem(item_info);
        if (it == null) {
            printFailure();
            System.out.println(" найти \"" + item_info + "\".");
            return false;
        }
        inventory.putItem(it);
        System.out.println(name + " подобрал" + ((sex == Sex.FEMALE)? "а": "") +
                " \"" + it + "\".");
        return true;
    }

    public boolean takeItemFromCont(String item_info, String cont_info) {
        if (!possibleToTake(item_info)) return false;
        Item it = cur_loc.getItem(item_info, cont_info);
        if (it == null) {
            printFailure();
            System.out.println(" найти \"" + item_info + "\" в \"" + cont_info + "\".");
            return false;
        }
        inventory.putItem(it);
        System.out.println(name + " взял" + ((sex == Sex.FEMALE)? "а": "") +
                " \"" + it + "\" из \"" + cont_info + "\".");
        return true;
    }

    public void throwItem(String item_info) {
        Item it = inventory.getItem(item_info);
        if (it != null) {
            cur_loc.putItem(it);
        }
        System.out.println(name + " выбросил" + ((sex == Sex.FEMALE)? "а": "") +
                " \"" + it + "\".");
    }

    public void putItem(String item_info, String cont_info) {
        Item it = getItem(item_info);
        if (it == null)
            return;
        if (!cur_loc.putItem(it, cont_info)) {
            printFailure();
            System.out.println(" положить \"" + it + "\" в  \"" + cont_info + "\".");
            takeItem(it);
            return;
        }
        System.out.println(name + " положил" + ((sex == Sex.FEMALE)? "а": "") +
                " \"" + it + "\" в \"" + cont_info + "\".");
        return;
    }

    public void giveItem(String item_info, Human h) {
        Item it = getItem(item_info);
        if (h.getLocation() != cur_loc) {
            printFailure();
            System.out.print(" отдать \"" + it + "\": ");
            System.out.println(name + " и " + name + " находятся в разных локациях.");
        }
        if (it == null)
            return;
        if (!h.takeItem(it)) takeItem(it);
        else
            System.out.println(name + " дал" + ((sex == Sex.FEMALE)? "а": "") +
                    " \"" + it + "\" персонажу " + h + ".");
        return;
    }

    public void applyToItemInLoc(String item_info, String obj_info) {
        apply(item_info, obj_info, cur_loc);
    }

    public void applyToItemInInv(String item_info, String obj_info) {
        apply(item_info, obj_info, inventory);
    }

    private void apply(String item_info, String obj_info, iItemContainer cont) {
        Item it = getItem(item_info);
        if (it == null)
            return;
        Item obj = cont.getItem(obj_info);
        if (obj == null) {
            printFailure();
            System.out.println(" найти \"" + obj_info + "\".");
            takeItem(it);
            return;
        }
        System.out.println(name + " пытается " +
                           ((Applicable)it).getVerb() +
                           " \"" + obj + "\".");
        obj = ((Applicable)it).apply(obj);
        cont.putItem(obj);
        return;
    }

    public void expel(Human h) {
        if (h.getLocation() == cur_loc) {
            System.out.println(name + " выгоняет " + h + ".");
            h.move(Location.OUTDOOR);
            return;
        }
        printFailure();
        System.out.println(" выгнать " + h + ", потому что " +
                           h + " находится в другой локации.");
        return;
    }

    public void laugh() {
        System.out.println("АХАХАХАХАХАХАХАХАХАХА");
        System.out.println("ахахах");
        System.out.println("АААААААА-ХА-ХА-ХА-ХАХАХАХАХАХАХА");
        System.out.println("Ор");
        System.out.println("(это смеял" +
                           ((sex == Sex.FEMALE)? "ась": "ся") +
                           " " + name + ")");
    }

    public void say(String speech) {
        System.out.print("-- ");
        System.out.print(speech);
        String l_c = "";
        l_c += (speech.charAt(speech.length() - 1));
        if (!".,?!:;".contains(l_c)) System.out.print(",");
        System.out.println(" -- сказал" +
                           ((sex == Sex.FEMALE)? "а": "") +
                           " " + name + ".");
    }

    public void lookForward(String obj) {
        System.out.println("*" + name + " ёрзает от нетерпения*");
        say("Жду не дождусь " + obj);
    }

    public void beHappy() {System.out.println("*" + name + " светится от счастья*");}

    public void read(String item_info) {
        Item writing = getItem(item_info);
        if (writing == null) return;
        if (writing instanceof Writing) {
            System.out.print(name + " читает \"" + writing + "\":\n\t\"");
            String[] text = ((Writing)writing).getText();
            for (int i = 0; i < text.length - 1; i++) {
                System.out.println(text[i]);
            }
            System.out.println(text[text.length - 1] + "\"");
        }
        else
            System.out.println(writing + "невозможно прочитать.");
        inventory.putItem(writing);
    }

    public void readQuiet(String item_info) {
        Item writing = getItem(item_info);
        if (writing == null) return;
        if (writing instanceof Writing)
            System.out.println(name + " читает \"" + writing + "\".");
        else
            System.out.println(writing + "невозможно прочитать.");
        inventory.putItem(writing);
    }

    public void receivePost() {
        PostBox pb = (PostBox) cur_loc.getItem("Почтовый ящик");
        Writing[] post = pb.receivePost();
        int got_writings = Math.min(inventory.getCapacity() - inventory.getSize(),
                                    post.length);
        for (int i = 0; i < got_writings; i++)
            inventory.putItem(post[i]);
        for (int i = got_writings; i < post.length; i++)
            cur_loc.putItem(post[i]);
        System.out.println(name + " получил" +
                           ((sex == Sex.FEMALE)? "а": "") +
                           " " + got_writings + " писем.");
        if (got_writings != post.length)
            System.out.println(post.length - got_writings + " писем выпало на пол.");
        return;
    }
}
