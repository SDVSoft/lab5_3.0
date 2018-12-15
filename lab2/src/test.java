import ru.ifmo.se.pokemon.*;


public class test {
	public static void main(String[] args) {
		Battle b = new Battle();
		TB2 p1 = new TB2("Bull", 10);
		p1.sMove(new Facade());
		TB2 p2 = new TB2("Tap", 10);
		p2.sMove(new RockSlide());
		System.out.println("Bull's HP -- " + p1.getHP());
		System.out.println("Tap's HP -- " + p2.getHP());
		b.addAlly(p1);
		b.addFoe(p2);
		b.go();
	}
}
