public class Coordinates {
    private Long x; //Поле не может быть null
    private double y;

    public Coordinates(Long x, double y) throws NullPointerException {
        if (x == null) throw new NullPointerException("Поле Coordinates.x не может иметь значение null.");
        this.x = x;
        this.y = y;
    }

    public String toString() {return "x = " + x + "; y = " + y + ".";}

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Coordinates) {
            Coordinates other = (Coordinates)obj;
            return this.x.equals(other.x) && this.y == other.y;
        }
        return false;
    }

    public int hashCode() {return x.hashCode() + Double.hashCode(y);}

    public Long getX() {return x;}

    public double getY() {return y;}

    public void cX(Long x) {this.x = x;}

    public static void main(String args[]) {}
}
