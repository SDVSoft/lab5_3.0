import ru.ifmo.se.pokemon.*;

/**
 * Energy Ball deals damage and has a 10% chance
 * of lowering the target's Special Defense by one stage.
 */
public class EnergyBall extends SpecialMove {
	private static final double ACCURACY = 100;
	private static final double POWER = 90;
	private static final Type TYPE = Type.GRASS;
	
	EnergyBall() {
		super(TYPE, POWER, ACCURACY);
	}
	
	EnergyBall(int priority, int hits) {
		super(TYPE, POWER, ACCURACY, priority, hits);
	}
	
	protected void applyOppEffects(Pokemon p) {
		p.addEffect(new Effect().chance(0.1).turns(-1).stat(Stat.SPECIAL_DEFENSE, -1));
	}
	
	protected String describe() {
		return "применяет атаку Energy Ball";
	}
	
	public static void main(String args[]) {
		
	}
}
