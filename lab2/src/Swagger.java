import ru.ifmo.se.pokemon.*;

/**
 * Swagger confuses the target and raises its Attack by two stages.
 */
public class Swagger extends StatusMove {
	private static final double ACCURACY = 85;
	private static final double POWER = 0;
	private static final Type TYPE = Type.NORMAL;
	
	Swagger() {
		super(TYPE, POWER, ACCURACY);
	}
	
	Swagger(int priority, int hits) {
		super(TYPE, POWER, ACCURACY, priority, hits);
	}
	
	protected void applyOppEffects(Pokemon p) {
		Effect.confuse(p);
		p.setMod(Stat.ATTACK, 2);
	}
	
	protected String describe() {
		return "применяет атаку Swagger";
	}
	
	public static void main(String args[]) {
		
	}
}
