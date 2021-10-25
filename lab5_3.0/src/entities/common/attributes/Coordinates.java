package entities.common.attributes;

/**
 * Coordinates is a class that represents the geographic coordinates of an object
 */
public class Coordinates implements Cloneable, Comparable<Coordinates> {
    private double x;
    private Double y; //Поле не может быть null

    /**
     * Initializes a new Coordinates (x, y)
     * @param x - coordinate x
     * @param y - coordinate y
     */
    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the string representation of this Coordinates
     * @return The string representation of this Coordinates
     */
    public String toString() { return "(" + x + ", " + y + ")"; }

    /**
     * Compares this Coordinates to the specified object. The result is true if and only
     * if the argument is not null and is a Coordinates object that represents the same
     * coordinates as this object (this.x == other.x && this.y.equals(other.y)).
     * @param obj - The object to compare this Coordinates against
     * @return true if the given object represents a Coordinates equivalent to this
     * Coordinates, false otherwise
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Coordinates) {
            Coordinates other = (Coordinates)obj;
            return this.x == other.x && this.y.equals(other.y);
        }
        return false;
    }

    /**
     * Compares this Coordinates to another. Coordinates are ordered first by x then by y.
     * The result is a positive integer if this Coordinates object follows the argument
     * Coordinates. The result is zero if the coordinates are equal; compareTo returns 0
     * exactly when the equals(Object) method would return true. The result is a negative
     * integer if this Coordinates object precedes the argument Coordinates.
     * @param other - the Coordinates to be compared.
     * @return the value 0 if the argument Coordinates is equal to this Coordinates;
     * a value less than 0 if this Coordinates is less than the Coordinates argument;
     * and a value greater than 0 if this Coordinates is greater than the Coordinates
     * argument.
     */
    public int compareTo(Coordinates other) {
        if (Double.compare(x, other.x) == 0)
            return y.compareTo(other.y);
        return Double.compare(x, other.x);
    }

    /**
     * Returns a hash code for this Coordinates. The hash code for a Coordinates object is
     * computed as Double.hashCode(x) + y.hashCode()
     * @return a hash code value for this object.
     */
    public int hashCode() { return Double.hashCode(x) + y.hashCode(); }

    /**
     * Returns a clone of this Coordinates
     * @return A clone of this Coordinates
     */
    public Coordinates clone() {
        try {
            return (Coordinates) super.clone();
        } catch (CloneNotSupportedException cnse) {
            System.out.println("CloneNotSupportedException was caught while cloning Coordinates.");
            return null;
        }
    }

    /**
     * Returns x coordinate
     * @return x coordinate
     */
    public double getX() { return x; }

    /**
     * Returns y coordinate
     * @return y coordinate
     */
    public Double getY() { return y; }
}
