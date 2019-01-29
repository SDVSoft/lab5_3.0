import ru.ifmo.se.pokemon.*;

/**
 * Shadow Ball deals damage and has a 20% chance
 * of lowering the target's Special Defense by one stage.
 */
public class ShadowBall extends SpecialMove {
    private static final double ACCURACY = 100;
    private static final double POWER = 80;
    private static final Type TYPE = Type.GHOST;

    ShadowBall() {
        super(TYPE, POWER, ACCURACY);
    }

    ShadowBall(int priority, int hits) {
        super(TYPE, POWER, ACCURACY, priority, hits);
    }

    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().chance(0.2).turns(-1).stat(Stat.SPECIAL_DEFENSE, -1));
    }

    protected String describe() {
        return "применяет атаку Shadow Ball";
    }

    public static void main(String args[]) {

    }
}
