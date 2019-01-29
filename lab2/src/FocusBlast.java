import ru.ifmo.se.pokemon.*;

/**
 * Focus Blast deals damage and has a 10% chance
 * of lowering the target's Special Defense by one stage.
 */
public class FocusBlast extends SpecialMove {
    private static final double ACCURACY = 70;
    private static final double POWER = 120;
    private static final Type TYPE = Type.FIGHTING;

    FocusBlast() {
        super(TYPE, POWER, ACCURACY);
    }

    FocusBlast(int priority, int hits) {
        super(TYPE, POWER, ACCURACY, priority, hits);
    }

    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().chance(0.1).turns(-1).stat(Stat.SPECIAL_DEFENSE, -1));
    }

    protected String describe() {
        return "применяет атаку Focus Blast";
    }

    public static void main(String args[]) {

    }
}
