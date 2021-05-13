package Parameters;

public class Coordinates implements Cloneable {
    private Long x; //Поле не может быть null
    private double y;

    public Coordinates(long x, double y) throws NullPointerException {
        //if (x == null) throw new NullPointerException("Поле Parameters.Coordinates.x не может иметь значение null.");
        this.x = x;
        this.y = y;
    }

    public String toString() {return "(" + x + ", " + y + ")";}

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Coordinates) {
            Coordinates other = (Coordinates)obj;
            return this.x.equals(other.x) && this.y == other.y;
        }
        return false;
    }

    public int hashCode() {return x.hashCode() + Double.hashCode(y);}

    public Coordinates clone() {
        try{
            return (Coordinates) super.clone();
        } catch (CloneNotSupportedException cnse) {
            System.out.println("CloneNotSupportedException was catched while cloning Coordinates.");
            return null;
        }
    }

    public Long getX() {return x;}

    public double getY() {return y;}

    public static void main(String args[]) {}
}
