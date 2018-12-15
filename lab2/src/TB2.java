import ru.ifmo.se.pokemon.*;

public class TB2 extends TapuBulu {
	private final double HP = 90;
	private final double ATTACK = 230;
	private final double DEFENSE = 215;
	private final double SPECIAL_ATTACK = 65;
	private final double SPECIAL_DEFENSE = 195;
	private final double SPEED = 135;
	private final Move[] ORIGINAL_MOVES = {new ShadowBall()};
	
	public TB2() {
		super();
		setData();
	}
	
	public TB2(String name, int level) {
		super(name, level);
		setData();
	}
	
	private void setData() {
		setStats(HP, ATTACK, DEFENSE, SPECIAL_ATTACK, SPECIAL_DEFENSE, SPEED);
		for (Move m : ORIGINAL_MOVES) {
			addMove(m);
		}
	}
	
	public Move getMove() {
		return super.getPreparedMove();
	}
	
	public void sMove(Move...moves) {
		super.setMove(moves);
	}
	
	public static void main(String args[]) {
		TB2 tb2 = new TB2();
		TapuBulu t = new TapuBulu();
		Stat[] stats = {Stat.HP, Stat.ATTACK, Stat.DEFENSE};
		for (Stat s : stats) {
			System.out.println("tb2's " + s + " -- " + tb2.getStat(s));
			System.out.println("t's " + s + " -- " + t.getStat(s));
			System.out.println();
		}
	}
}
