public class HumanWithPropeller extends Human implements Helicopter {

    HumanWithPropeller(String name, Sex sex, Location spawn_point) {
        super(name, sex, spawn_point);
    }

    public void fly() {
        System.out.println("*звуки пропеллера*");
    }

    public void fly(Location dest) {
        fly();
        changeLoc(dest);
        printer.printChangeLoc((getSex() == Sex.MALE)? "перелетел" : "перелетела", dest);
    }
}
