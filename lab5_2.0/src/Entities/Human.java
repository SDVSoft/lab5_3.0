package Entities;

import Exceptions.InvalidHumanHeightException;

public class Human implements Cloneable {
    private double height; //Значение поля должно быть больше 0

    public Human(double height) throws InvalidHumanHeightException {
        setHeight(height);
    }


    public void setHeight(double height) {
        if (height > 0) this.height = height;
        else throw new InvalidHumanHeightException();
    }

    public double getHeight() {
        return height;
    }

    public Human clone() {
        try {
            return (Human) super.clone();
        } catch(CloneNotSupportedException cnse) {
            System.out.println("CloneNotSupportedException was catched while cloning Human.");
            return null;
        }
    }

    public String toString(){return "Человек высотой " + height;}

    public boolean equals(Object obj) {return super.equals(obj);}

    public int hashCode(){return super.hashCode();}
}