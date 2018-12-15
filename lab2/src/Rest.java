import ru.ifmo.se.pokemon.*;

/**
 * User sleeps for 2 turns, but user is fully healed.
 */
public class Rest extends StatusMove {
	private static final double ACCURACY = 100;
	private static final double POWER = 0;
	private static final Type TYPE = Type.PSYCHIC;
	
	Rest() {
		super(TYPE, POWER, ACCURACY);
	}
	
	Rest(int priority, int hits) {
		super(TYPE, POWER, ACCURACY, priority, hits);
	}
	
	protected void applySelfEffects(Pokemon p) {
		p.restore();
		p.addEffect(new Effect().turns(2).condition(Status.SLEEP));
	}
	
	protected String describe() {
		return "применяет атаку Rest";
	}
	
	public static void main(String args[]) {
		
	}
}
