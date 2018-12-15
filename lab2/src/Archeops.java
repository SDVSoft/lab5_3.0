import ru.ifmo.se.pokemon.*;

/**
 * Rock/Flying type Pokemon inherited from Archen.
 * Original moves: FocusBlast.
 */
public class Archeops extends Archen {
	private final double HP = 75;
	private final double ATTACK = 140;
	private final double DEFENSE = 65;
	private final double SPECIAL_ATTACK = 112;
	private final double SPECIAL_DEFENSE = 65;
	private final double SPEED = 110;
	protected final Type[] TYPES = {Type.ROCK, Type.FLYING};
	private final Move[] ORIGINAL_MOVES = {new FocusBlast()};
	
	public Archeops() {
		super();
		setData();
	}
	
	public Archeops(String name, int level) {
		super(name, Math.max(51, level));
		setData();
	}
	
	private void setData() {
		setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED);
		setType(TYPES);
		setMove(ORIGINAL_MOVES);
	}
}
