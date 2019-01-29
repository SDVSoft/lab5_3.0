import ru.ifmo.se.pokemon.*;

/**
 * Smog deals damage and has a 40% chance of poisoning the target.
 */
public class Smog extends SpecialMove {
    private static final double ACCURACY = 70;
    private static final double POWER = 30;
    private static final Type TYPE = Type.POISON;

    Smog() {
        super(TYPE, POWER, ACCURACY);
    }

    Smog(int priority, int hits) {
        super(TYPE, POWER, ACCURACY, priority, hits);
    }

    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().chance(0.4).condition(Status.POISON));
    }

    protected String describe() {
        return "применяет атаку Smog";
    }

    public static void main(String args[]) {

    }
}
