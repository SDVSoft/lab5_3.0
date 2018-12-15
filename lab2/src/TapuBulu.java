import ru.ifmo.se.pokemon.*;

/**
 * Grass/Fairy type Pokemon.
 * Original moves: EnergyBall, Tickle, Facade, ShadowBall.
 */
public class TapuBulu extends Pokemon {
	private final double HP = 70;
	private final double ATTACK = 130;
	private final double DEFENSE = 115;
	private final double SPECIAL_ATTACK = 85;
	private final double SPECIAL_DEFENSE = 95;
	private final double SPEED = 75;
	protected final Type[] TYPES = {Type.GRASS, Type.FAIRY};
	private final Move[] ORIGINAL_MOVES = {new EnergyBall(), new Tickle(), new Facade(), new ShadowBall()};
	
	public TapuBulu() {
		super();
		setData();
	}
	
	public TapuBulu(String name, int level) {
		super(name, level);
		setData();
	}
	
	private void setData() {
		setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED);
		setType(TYPES);
		setMove(ORIGINAL_MOVES);
	}
}
