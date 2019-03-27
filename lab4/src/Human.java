public class Human {
    private String name;
    private Sex sex;
    private Location cur_loc;
    private LootBag inventory = new Pockets();
    protected HumanPrinter printer = new HumanPrinter();

    public Human(String name, Sex sex, Location spawn_point) {
        this.name = name;
        this.sex = sex;
        cur_loc = spawn_point;
        HumanList.newHuman(name, sex);
    }

    protected class HumanPrinter {
        public void printChangeLoc(String mov_act, Location dest) {
            System.out.println(name + " " + mov_act + " в новую локацию: \"" + dest + "\".");
        }

        public String failureToString(String action) {
            return name + " не смог" + ((sex == Sex.FEMALE)? "ла ": " ") + action + ".";
        }

        public void printFailure(String action) {
            System.out.println(failureToString(action));
        }
    }

    public static class HumanList {
        private static String[] human_names = new String[1000];
        private static Sex[] human_sex = new Sex[1000];
        private static int human_num = 0;

        public static void newHuman(String name, Sex sex) {
            human_names[human_num] = name;
            human_sex[human_num++] = sex;
        }

        public static void printList() {
            for (int i = 0; i < human_num; i++) {
                System.out.println(human_names[i] + " " + human_sex[i]);
            }
        }

        public static int getTheNumberOfHuman() {return human_num;}
    }

    public String toString() {return name;}

    public int hashCode() {return name.hashCode();}

    public Sex getSex() {return sex;}

    public Location getLocation() {return cur_loc;}

    public String[] getInventoryContentInfo() {return inventory.getContentInfo();}

    private Item getItem(String item_info) throws ItemNotFoundException {
        Item it = inventory.getItem(item_info);
        if (it == null)
            throw new ItemNotFoundException(name + " не имеет \"" + item_info +
                                            "\" в своём инвентаре.");
        return it;
    }

    protected void changeLoc(Location dest) {
        cur_loc.exit(this);
        dest.enter(this);
        cur_loc = dest;
    }

    public void move(Location dest) {
        changeLoc(dest);
        printer.printChangeLoc((sex == Sex.MALE)? "перешёл"  : "перешла", dest);
    }

    public void run() {System.out.println(name + " носится, как угорел" +
                                          ((sex == Sex.MALE)? "ый"  : "ая") + ".");}

    public void run(Location dest) {
        changeLoc(dest);
        printer.printChangeLoc((sex == Sex.MALE)? "побежал"  : "побежала", dest);
    }

    public boolean possibleToTake(String item_info) {
        if (inventory.isFull()) {
            printer.printFailure("взять \"" + item_info + "\", так как заполнил" +
                         ((sex == Sex.FEMALE)? "а": "") + " свой инвентарь");
            return false;
        }
        return true;
    }

    public boolean takeItem(Item it) {
        if (possibleToTake(it.toString()))
            return inventory.putItem(it);
        return false;
    }

    public boolean takeItemFromLoc(String item_info) throws ItemNotFoundException {
        if (!possibleToTake(item_info)) return false;
        Item it = cur_loc.getItem(item_info);
        if (it == null) {
            throw new ItemNotFoundException(printer.failureToString("найти \"" + item_info + "\""));
        }
        inventory.putItem(it);
        System.out.println(name + " подобрал" + ((sex == Sex.FEMALE)? "а": "") +
                " \"" + it + "\".");
        return true;
    }

    public boolean takeItemFromCont(String item_info, String cont_info) throws ItemNotFoundException {
        if (!possibleToTake(item_info)) return false;
        Item it = cur_loc.getItem(item_info, cont_info);
        if (it == null) {
            throw new ItemNotFoundException(printer.failureToString("найти \"" + item_info + "\" в \"" +
                                            cont_info + "\""));
        }
        inventory.putItem(it);
        System.out.println(name + " взял" + ((sex == Sex.FEMALE)? "а": "") +
                " \"" + it + "\" из \"" + cont_info + "\".");
        return true;
    }

    public void throwItem(String item_info) throws ItemNotFoundException {
        Item it = inventory.getItem(item_info);
        if (it == null)
            throw new ItemNotFoundException(name + " не имеет \"" + item_info +
                    "\" в своём инвентаре.");
        cur_loc.putItem(it);
        System.out.println(name + " выбросил" + ((sex == Sex.FEMALE)? "а": "") +
                " \"" + it + "\".");
    }

    public void putItem(String item_info, String cont_info) throws ItemNotFoundException {
        Item it = getItem(item_info);
        if (!cur_loc.putItem(it, cont_info)) {
            printer.printFailure("положить \"" + it + "\" в  \"" + cont_info + "\"");
            takeItem(it);
            return;
        }
        System.out.println(name + " положил" + ((sex == Sex.FEMALE)? "а": "") +
                " \"" + it + "\" в \"" + cont_info + "\".");
        return;
    }

    public void giveItem(String item_info, Human h) throws ItemNotFoundException {
        Item it = getItem(item_info);
        if (h.getLocation() != cur_loc) {
            printer.printFailure("отдать \"" + it + "\": " + name + " и " +
                         name + " находятся в разных локациях");
            takeItem(it);
            return;
        }
        if (!h.takeItem(it)) takeItem(it);
        else
            System.out.println(name + " дал" + ((sex == Sex.FEMALE)? "а": "") +
                    " \"" + it + "\" персонажу " + h + ".");
        return;
    }

    public void applyToItemInLoc(String item_info, String obj_info) throws ItemNotFoundException {
        apply(item_info, obj_info, cur_loc);
    }

    public void applyToItemInInv(String item_info, String obj_info) throws ItemNotFoundException {
        apply(item_info, obj_info, inventory);
    }

    private void apply(String item_info, String obj_info, iItemContainer cont) throws ItemNotFoundException {
        Item it = getItem(item_info);
        if (!(it instanceof Applicable)) {
            printer.printFailure("воспользоваться \"" + item_info +
                         "\", потому что не знает, что с ним делать");
            return;
        }
        Item obj = cont.getItem(obj_info);
        System.out.println(name + " пытается " +
                ((Applicable)it).getVerb() +
                " \"" + obj + "\".");
        cont.putItem(((Applicable)it).apply(obj));
        takeItem(it);
        return;
    }

    public void expel(Human h) {
        if (h.getLocation() == cur_loc) {
            System.out.println(name + " выгоняет " + h + ".");
            h.move(Location.OUTDOOR);
            return;
        }
        printer.printFailure("выгнать " + h + ", потому что " +
                     h + " находится в другой локации");
        return;
    }

    public void laugh() {
        class SoundsOfLaughing {
            private String sounds = "АХАХАХАХАХАХАХАХАХАХА\n" + "ахахах\n" +
                                    "АААААААА-ХА-ХА-ХА-ХАХАХАХАХАХАХА\n" + "Ор";

            public void printSounds() {System.out.println(sounds);}

            public String getSounds() {return sounds;}
        }
        SoundsOfLaughing sounds = new SoundsOfLaughing();
        sounds.printSounds();
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

    public void suspect() {System.out.println("*" + name + " что-то подозревает*");}

    public void read(String item_info) throws NotAWritingException, ItemNotFoundException{
        Applicable reader = new Applicable() {
            @Override
            public Item apply(Item it) {
                System.out.print(name + " читает \"" + it + "\":\n\t\"");
                String[] text = ((Writing)it).getText();
                for (int i = 0; i < text.length - 1; i++) {
                    System.out.println(text[i]);
                }
                System.out.println(text[text.length - 1] + "\"");
                return it;
            }

            @Override
            public String getVerb() {
                return "читать";
            }
        };

        Item writing = getItem(item_info);
        try {
            if (writing instanceof Writing) {
                reader.apply(writing);
            } else
                throw new NotAWritingException();
        }
        finally {
            inventory.putItem(writing);
        }
    }

    public void readQuiet(String item_info) throws NotAWritingException, ItemNotFoundException {
        Item writing = getItem(item_info);
        try {
            if (writing instanceof Writing)
                System.out.println(name + " читает \"" + writing + "\".");
            else
                throw new NotAWritingException();
        }
        finally {
            inventory.putItem(writing);
        }
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

    public void openDoor() {
        if (!cur_loc.hasDoor()) {
            printer.printFailure("найти дверь, которую можно было бы открыть");
            return;
        }
        if (cur_loc.isDoorOpen()) {
            printer.printFailure("открыть уже открытую дверь");
            return;
        }
        cur_loc.openDoor();
        System.out.println(name + " открыл" + ((sex == Sex.FEMALE)? "а": "") +
                           " дверь в " + "\"" + cur_loc + "\".");
    }

    public void closeDoor() {
        if (!cur_loc.hasDoor()) {
            printer.printFailure("найти дверь, которую можно было бы закрыть");
            return;
        }
        if (!cur_loc.isDoorOpen()) {
            printer.printFailure("закрыть уже закрытую дверь");
            return;
        }
        cur_loc.closeDoor();
        System.out.println(name + " закрыл" + ((sex == Sex.FEMALE)? "а": "") +
                           " дверь в " + "\"" + cur_loc + "\".");
    }

    public void removeDoor() {
        if (!cur_loc.hasDoor()) {
            printer.printFailure("найти дверь, которую можно было бы демонтировать");
            return;
        }
        inventory.putItem(cur_loc.removeDoor());
        System.out.println(name + " демонтировал" + ((sex == Sex.FEMALE)? "а": "") +
                           " дверь в " + "\"" + cur_loc + "\".");
    }

    public void setDoor(String item_info) throws ItemNotFoundException {
        Item it = getItem(item_info);
        if (!(it instanceof iDoor)) {
            printer.printFailure("установить " + it + " в качестве двери");
            return;
        }
        cur_loc.setDoor((iDoor) it);
        System.out.println(name + " утсановил" + ((sex == Sex.FEMALE)? "а": "") +
                " " + it + " в " + "\"" + cur_loc + "\" в качестве двери.");
    }

    public void eat(String item_info) {
        Item food = getItem(item_info);
        if (food instanceof Food) {
            System.out.println(name + " употребляет \"" + food + "\".");
        }
        else {
            System.out.println(name + " хотел съесть \"" + food + "\", но это несъедобно.");
            takeItem(food);
        }
    }
}
