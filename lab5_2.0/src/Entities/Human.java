package Entities;

import Exceptions.InvalidHumanHeightException;

/**
 * Class Human represents humans.
 */
public class Human implements Cloneable {
    private double height; //Значение поля должно быть больше 0

    /**
     * Creates new Human with specified height. Height must be greater than 0.
     * @param height - height value
     * @throws InvalidHumanHeightException if height 0 or less.
     */
    public Human(double height) throws InvalidHumanHeightException {
        setHeight(height);
    }

    /**
     * Sets new height to this Human. Height must be greater than 0.
     * @param height - height value
     * @throws InvalidHumanHeightException if height 0 or less.
     */
    public void setHeight(double height) {
        if (height > 0) this.height = height;
        else throw new InvalidHumanHeightException();
    }

    /**
     * Returns height of this Human
     * @return height of this Human
     */
    public double getHeight() {
        return height;
    }

    /**
     * Makes clone of this Human (see Object.clone())
     * @return clone of this Human.
     */
    public Human clone() {
        try {
            return (Human) super.clone();
        } catch(CloneNotSupportedException cnse) {
            System.out.println("CloneNotSupportedException was catched while cloning Human.");
            return null;
        }
    }

    /**
     * Returns a string representation of this Human
     * @return a string representation of this Human
     */
    public String toString(){return "Человек высотой " + height;}

    /**
     * Compares two Humans according to their height
     * @param obj - the Human to be compared
     * @return the value 0 if the argument human has the same height to this human; a
     * value less than 0 if this humna is lower than the human argument; and a value
     * greater than 0 if this human is higher than the human argument.
     */
    public boolean equals(Object obj) {return super.equals(obj);}

    /**
     * Returns a hash code for this human. The hash code for a Human object is computed as
     * Human.getHeight().hashCode()
     * @return a hash code value for this human.
     */
    public int hashCode(){return super.hashCode();}
}