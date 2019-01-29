import ru.ifmo.se.pokemon.*;

public class test {
    public static void main(String[] args) {
        Battle b = new Battle();
        b.addFoe(new TapuBulu("Tap", 5));
        b.addAlly(new Archen("Archi", 5));
        b.addAlly(new Archeops("Gena", 5));
        b.addAlly(new Litwick("Li", 5));
        b.addFoe(new Lampent("Nikita", 5));
        b.addFoe(new Chandelure("Pis'mak", 5));
        b.go();
    }
}