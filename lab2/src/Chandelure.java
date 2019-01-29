import ru.ifmo.se.pokemon.*;

/**
 * Ghost/Fire type Pokemon inherited from Lampent.
 * Original moves: FireBlast.
 */
public class Chandelure extends Lampent {
    private final double HP = 60;
    private final double ATTACK = 55;
    private final double DEFENSE = 90;
    private final double SPECIAL_ATTACK = 145;
    private final double SPECIAL_DEFENSE = 90;
    private final double SPEED = 80;
    protected final Type[] TYPES = {Type.GHOST, Type.FIRE};
    private final Move[] ORIGINAL_MOVES = {new FireBlast()};

    public Chandelure() {
        super();
        setData();
    }

    public Chandelure(String name, int level) {
        super(name, level);
        setData();
    }

    private void setData() {
        setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED);
        setType(TYPES);
        setMove(ORIGINAL_MOVES);
    }
}
