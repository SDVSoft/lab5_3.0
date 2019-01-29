import ru.ifmo.se.pokemon.*;

/**
 * Ghost/Fire type Pokemon.
 * Original moves: Rest, Smog.
 */
public class Litwick extends Pokemon {
    private final double HP = 50;
    private final double ATTACK = 30;
    private final double DEFENSE = 55;
    private final double SPECIAL_ATTACK = 65;
    private final double SPECIAL_DEFENSE = 55;
    private final double SPEED = 20;
    protected final Type[] TYPES = {Type.GHOST, Type.FIRE};
    private final Move[] ORIGINAL_MOVES = {new Rest(), new Smog()};

    public Litwick() {
        super();
        setData();
    }

    public Litwick(String name, int level) {
        super(name, Math.max(5, level));
        setData();
    }

    private void setData() {
        setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED);
        setType(TYPES);
        setMove(ORIGINAL_MOVES);
    }
}
