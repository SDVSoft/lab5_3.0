import ru.ifmo.se.pokemon.*;

/**
 * Tickle lowers the target's Attack and Defense by one stage each.
 */
public class Tickle extends StatusMove {
    private static final double ACCURACY = 100;
    private static final double POWER = 0;
    private static final Type TYPE = Type.NORMAL;

    Tickle() {
        super(TYPE, POWER, ACCURACY);
    }

    Tickle(int priority, int hits) {
        super(TYPE, POWER, ACCURACY, priority, hits);
    }

    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, -1);
        p.setMod(Stat.DEFENSE, -1);
    }

    protected String describe() {
        return "применяет атаку Tickle";
    }

    public static void main(String args[]) {

    }
}
