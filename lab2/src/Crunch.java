import ru.ifmo.se.pokemon.*;

/**
 * Crunch deals damage and has a 20% chance of lowering the target's Defense by one stage.
 */
public class Crunch extends PhysicalMove {
	private static final double ACCURACY = 100;
	private static final double POWER = 80;
	private static final Type TYPE = Type.DARK;
	
	Crunch() {
		super(TYPE, POWER, ACCURACY);
	}
	
	Crunch(int priority, int hits) {
		super(TYPE, POWER, ACCURACY, priority, hits);
	}
	
	protected void applyOppEffects(Pokemon p) {
		p.addEffect(new Effect().chance(0.2).stat(Stat.DEFENSE, -1));
	}
	
	protected String describe() {
		return "применяет атаку Crunch";
	}
	
	public static void main(String args[]) {
		
	}
}