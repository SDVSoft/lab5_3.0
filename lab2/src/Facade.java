import ru.ifmo.se.pokemon.*;

/**
 * Facade deals damage, and hits with double power (140)
 * if the user is burned, poisoned or paralyzed.
 * In the case of a burn, the usual attack-halving still occurs
 * so Facade hits with an effective power of 70.
 */
public class Facade extends PhysicalMove {
	private static final double ACCURACY = 100;
	private static final double POWER = 70;
	private static final Type TYPE = Type.NORMAL;
	
	Facade() {
		super(TYPE, POWER, ACCURACY);
	}
	
	Facade(int priority, int hits) {
		super(TYPE, POWER, ACCURACY, priority, hits);
	}
	
	/**
	 * If the attacking pokemon has BURN, POISON or PARALYZE status as a condition
	 * the same formula as in super.calcBaseDamage is used,
	 * with the difference that the energy is doubled.
	 * Otherwise super.calcBaseDamage is called.
	 */
	protected double calcBaseDamage(Pokemon att, Pokemon def) {
		Status attCond = att.getCondition();
		if (attCond == Status.BURN || attCond == Status.POISON || attCond == Status.PARALYZE)
			return (0.4 * att.getLevel() + 2.0) * power * 2.0 / 150.0;
		else
			return super.calcBaseDamage(att, def);
	}
	
	protected String describe() {
		return "применяет атаку Facade";
	}
	
	public static void main(String args[]) {
		
	}
}
