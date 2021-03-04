public class Human {
    private double height; //Значение поля должно быть больше 0

    public Human(double height) throws InvalidHumanHeightException{
        if (height > 0) this.height = height;
        else throw new InvalidHumanHeightException();
    }

    public String toString(){return "Человек высотой " + height + " метра.";}

    public boolean equals(Object obj) {return super.equals(obj);}

    public int hashCode(){return super.hashCode();}
}