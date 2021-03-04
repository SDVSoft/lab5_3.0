import java.util.ArrayList;

public class City implements Comparable<City> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float area; //Значение поля должно быть больше 0
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    private Float metersAboveSeaLevel;
    private Integer agglomeration;
    private Climate climate; //Поле не может быть null
    private StandardOfLiving standardOfLiving; //Поле не может быть null
    private Human governor; //Поле может быть null
    private static Long nextId = new Long(1);

    public City(String name,
                Coordinates coordinates,
                float area,
                int population,
                float metersAboveSeaLevel,
                int agglomeration,
                Climate climate,
                StandardOfLiving standardOfLiving,
                Human governor)
            throws InvalidCityDataException {
        if (name == null)
            throw new NullPointerException("Поле City.name не может иметь значение null.");
        else if (name.length() > 0)
            this.name = name;
        else
            throw new InvalidCityNameException();

        if (coordinates != null)
            this.coordinates = coordinates;
        else
            throw new NullPointerException("Поле City.coordinates не может иметь значение null.");

        if (area > 0)
            this.area = area;
        else
            throw new InvalidCityAreaException();

        if (population > 0)
            this.population = population;
        else
            throw new InvalidCityPopulationException();

        this.metersAboveSeaLevel = metersAboveSeaLevel;

        this.agglomeration = agglomeration;

        if (climate != null)
            this.climate = climate;
        else
            throw new NullPointerException("Поле City.climate не может иметь значение null.");

        if (standardOfLiving != null)
            this.standardOfLiving = standardOfLiving;
        else
            throw new NullPointerException("Поле City.standardOfLiving не может иметь значение null.");

        if (governor != null)
            this.governor = governor;
        else
            throw new NullPointerException("Поле City.governor не может иметь значение null.");

        id = nextId++;

        creationDate = new java.util.Date();
    }

    public String toString() {return name;}

    public boolean equals(Object obj) {
        if (obj instanceof City)
            return this.id.equals(((City)obj).id);
        return false;
    }

    public int hashCode() {return id.hashCode();}

    public int compareTo(City other) {
        return id.compareTo(other.id);
    }

    public Long getId() {
        return id;
    }

    public static void main(String args[]) throws InvalidCityDataException, InvalidHumanHeightException {
        City c1 = null, c2 = null;
        int a = 1;
        Integer A = a;
        Integer B = 1;
        System.out.println(A == B);
        Coordinates coord = new Coordinates(new Long(1), 2);
        System.out.println("nextId " + City.nextId);
        c1 = new City("c1", coord, 1, 1, 1, 1, Climate.HUMIDCONTINENTAL, StandardOfLiving.MEDIUM, new Human(1));
        System.out.println(c1.id);
        System.out.println(City.nextId);
        try {
            //c1 = new City("c1", coord, 1, null);
            //c2 = new City("c2", null, 1, A);
        } catch (NullPointerException NPE) {
            System.out.println("catch");
        }
        System.out.println(c1 + "\n" + c2);
        System.out.println(c1.coordinates);
        coord.cX(new Long(2));
        System.out.println(c1.coordinates);
        System.out.println(c1.creationDate);
/*
        Integer[] arr = new Integer[10];
        ArrayList<Integer> arrlist = new ArrayList<Integer>(arr);
*/
    }
}