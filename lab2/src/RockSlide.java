import ru.ifmo.se.pokemon.*;
import static java.lang.Math.*;

/**
 * Rock Slide deals damage and has a 30% chance of causing
 * the target to flinch.
 */
public class RockSlide extends PhysicalMove {
    private static final double ACCURACY = 90;
    private static final double POWER = 75;
    private static final Type TYPE = Type.ROCK;

    RockSlide() {
        super(TYPE, POWER, ACCURACY);
    }

    RockSlide(int priority, int hits) {
        super(TYPE, POWER, ACCURACY, priority, hits);
    }

    protected void applyOppEffects(Pokemon p) {
        if (random() >= 0.7)
            Effect.flinch(p);
    }

    protected String describe() {
        return "применяет атаку Rock Slide";
    }

    public static void main(String args[]) {

    }
}