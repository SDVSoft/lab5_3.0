public class Test {

    /*
    TODO: Exception extends ClassCastException
    TODO: Local class
     */

    public static void main(String args[]) {
        Human julius = new Human("дядя Юлиус", Sex.MALE, Location.LIVING_ROOM);
        julius.takeItem(new Coffee());
        Human bok = new Human("фрекен Бок", Sex.FEMALE, Location.LIVING_ROOM);
        bok.takeItem(new Coffee());
        Human kid = new Human("Mалыш", Sex.MALE, Location.LIVING_ROOM);
        Human postman = new Human("почтальон", Sex.MALE, Location.OUTDOOR);
        postman.takeItem(new Postcard("Боссе", "одна от Боссе"));
        postman.takeItem(new Postcard("Бетан", "другая от Бетан"));
        HumanWithPropeller karlson = new HumanWithPropeller("Карлсон", Sex.MALE,
                                                            Location.HOUSE_ON_THE_ROOF);
        Location.HOUSE_ON_THE_ROOF.putItem(new Drill(), "Стена");
        Location.MARKET.putItem(new BalticHerring());
        Location.ENTRANCE_HALL.putItem(new PostBox());

        kid.takeItem(new Door());

        bok.closeDoor();
        karlson.suspect();
        bok.eat("Кофе");
        julius.eat("Кофе");
        karlson.run(Location.LIVING_ROOM);

        julius.expel(karlson);
        bok.laugh();
        bok.say("Наконец-то перевес на моей стороне");
        julius.move(Location.DOCTOR);
        bok.move(Location.MARKET);
        bok.takeItemFromLoc("Салака");
        kid.move(Location.KID_ROOM);
        karlson.move(Location.HOUSE_ON_THE_ROOF);
        karlson.takeItemFromCont("Дрель", "Стена");
        karlson.fly(Location.KID_ROOM);
        kid.say("Эту дрель я уже видел. Нашёл чем удивить!");
        kid.lookForward("узнать, на кой ты её сюда припёр");
        postman.move(Location.ENTRANCE_HALL);
        postman.putItem("Открытка от Боссе", "Почтовый ящик");
        postman.putItem("Открытка от Бетан", "Почтовый ящик");
        postman.move(Location.OUTDOOR);
        kid.run(Location.ENTRANCE_HALL);
        kid.receivePost();
        kid.beHappy();
        String[] kid_items = kid.getInventoryContentInfo();
        karlson.move(Location.LIVING_ROOM);
        for (int j = 0; j < kid_items.length; j++) {
            try {
                kid.read(kid_items[j]);
            }
            catch(NotAWritingException NAWE) {
                NAWE.printStackTrace();
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < kid_items.length; j++) {
                try {
                    kid.readQuiet(kid_items[j]);
                }
                catch(NotAWritingException NAWE) {
                    NAWE.printStackTrace();
                }
            }
        }
        karlson.removeDoor();
        karlson.applyToItemInInv("Дрель", "Дверь");
        karlson.setDoor("Дырявая дверь");
        for (int j = 0; j < kid_items.length; j++) {
                try {
                    kid.readQuiet(kid_items[j]);
                }
                catch(NotAWritingException NAWE) {
                    NAWE.printStackTrace();
                }
        }
        kid.takeItemFromLoc("Ответ");
    }
}