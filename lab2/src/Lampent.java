import ru.ifmo.se.pokemon.*;

/**
 * Ghost/Fire type Pokemon inherited from Litwick.
 * Original moves: Ember.
 */
public class Lampent extends Litwick {
	private final double HP = 60;
	private final double ATTACK = 40;
	private final double DEFENSE = 60;
	private final double SPECIAL_ATTACK = 95;
	private final double SPECIAL_DEFENSE = 60;
	private final double SPEED = 55;
	protected final Type[] TYPES = {Type.GHOST, Type.FIRE};
	private final Move[] ORIGINAL_MOVES = {new Ember()};
	
	public Lampent() {
		super();
		setData();
	}
	
	public Lampent(String name, int level) {
		super(name, level);
		setData();
	}
	
	private void setData() {
		setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED);
		setType(TYPES);
		setMove(ORIGINAL_MOVES);
	}
}
