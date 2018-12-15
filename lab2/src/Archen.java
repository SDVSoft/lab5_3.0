import ru.ifmo.se.pokemon.*;

/**
 * Rock/Flying type Pokemon.
 * Original moves: RockSlide, Swagger, Crunch.
 */
public class Archen extends Pokemon {
	private final double HP = 55;
	private final double ATTACK = 112;
	private final double DEFENSE = 45;
	private final double SPECIAL_ATTACK = 74;
	private final double SPECIAL_DEFENSE = 45;
	private final double SPEED = 70;
	protected final Type[] TYPES = {Type.ROCK, Type.FLYING};
	private final Move[] ORIGINAL_MOVES = {new RockSlide(), new Swagger(), new Crunch()};
	
	public Archen() {
		super();
		setData();
	}
	
	public Archen(String name, int level) {
		super(name, Math.max(45, level));
		setData();
	}
	
	private void setData() {
		setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED);
		setType(TYPES);
		setMove(ORIGINAL_MOVES);
	}
}
