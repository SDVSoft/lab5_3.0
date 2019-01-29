import ru.ifmo.se.pokemon.*;

/**
 * Ember deals damage and has a 10% chance of burning the target.
 * Fire type Pokemon cannot be burned.
 */
public class Ember extends SpecialMove {
    private static final double ACCURACY = 100;
    private static final double POWER = 40;
    private static final Type TYPE = Type.FIRE;

    Ember() {
        super(TYPE, POWER, ACCURACY);
    }

    Ember(int priority, int hits) {
        super(TYPE, POWER, ACCURACY, priority, hits);
    }

    protected void applyOppEffects(Pokemon p) {
        if (!p.hasType(Type.FIRE))
            p.addEffect(new Effect().chance(0.1).condition(Status.BURN));
    }

    protected String describe() {
        return "применяет атаку Ember";
    }

    public static void main(String args[]) {

    }
}
