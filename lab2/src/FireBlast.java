import ru.ifmo.se.pokemon.*;

/**
 * Fire Blast deals damage and has a 10% chance of burning the target.
 * Fire type Pokemon cannot be burned.
 */
public class FireBlast extends SpecialMove {
    private static final double ACCURACY = 85;
    private static final double POWER = 110;
    private static final Type TYPE = Type.FIRE;

    FireBlast() {
        super(TYPE, POWER, ACCURACY);
    }

    FireBlast(int priority, int hits) {
        super(TYPE, POWER, ACCURACY, priority, hits);
    }

    protected void applyOppEffects(Pokemon p) {
        if (!p.hasType(Type.FIRE))
            p.addEffect(new Effect().chance(0.1).condition(Status.BURN));
    }

    protected String describe() {
        return "применяет атаку Fire Blast";
    }

    public static void main(String args[]) {

    }
}
