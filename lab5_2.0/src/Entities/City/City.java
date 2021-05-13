package Entities.City;

import Entities.Human;
import Exceptions.*;
import Parameters.Climate;
import Parameters.Coordinates;
import Parameters.StandardOfLiving;

import java.util.Date;

public class City implements Comparable<City> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private float area; //Значение поля должно быть больше 0
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    private Float metersAboveSeaLevel;
    private Integer agglomeration;
    private Climate climate; //Поле не может быть null
    private StandardOfLiving standardOfLiving; //Поле не может быть null
    private Human governor; //Поле может быть null
    private final java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    //private static Long nextId = new Long(1);
/*
    public Entities.City.City(long id,
                String name,
                Parameters.Coordinates coordinates,
                float area,
                int population,
                float metersAboveSeaLevel,
                int agglomeration,
                Parameters.Climate climate,
                Parameters.StandardOfLiving standardOfLiving,
                Entities.Human governor)
            throws InvalidCityDataException {
        this(id, name, coordinates, area, population, metersAboveSeaLevel,
             agglomeration, climate, standardOfLiving, governor, new java.util.Date());
    }
*/
    public City(long id,
                String name,
                Coordinates coordinates,
                float area,
                int population,
                float metersAboveSeaLevel,
                int agglomeration,
                Climate climate,
                StandardOfLiving standardOfLiving,
                Human governor,
                java.util.Date creationDate)
            throws InvalidCityDataException, NullPointerException {
        if (id > 0)
            this.id = id;
        else
            throw new InvalidCityIdException();

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

        this.governor = governor;

        this.creationDate = creationDate;
    }

    public String toString() {return name;}

    public boolean equals(Object obj) {
        //Criteria of equality: population, area and agglomeration
        if (obj instanceof City) {
            City other = (City) obj;
            return population.equals(other.population) &&
                   area == other.area &&
                   agglomeration.equals(other.agglomeration);
        }
        return false;
    }

    public int hashCode() {
        return population.hashCode() + Float.hashCode(area) + agglomeration.hashCode();
    }

    public int compareTo(City other) {
        //Comparison order: population -> area -> agglomeration
        int result = population.compareTo(other.population);
        if (result == 0) {
            result = Float.compare(area, other.area);
            if (result == 0) {
                return agglomeration.compareTo(other.agglomeration);
            }
        }
        return result;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates.clone();
    }

    public float getArea() {
        return area;
    }

    public Integer getPopulation() {
        return population;
    }

    public Float getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public Integer getAgglomeration() {
        return agglomeration;
    }

    public Climate getClimate() {
        return climate;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public Human getGovernor() {
        return governor.clone();
    }

    public Date getCreationDate() {
        return (Date) creationDate.clone();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public void setMetersAboveSeaLevel(Float metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setAgglomeration(Integer agglomeration) {
        this.agglomeration = agglomeration;
    }

    public void setClimate(Climate climate) {
        this.climate = climate;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }

    public void setGovernor(Human governor) {
        this.governor = governor;
    }

    public static void main(String[] args) throws InvalidCityDataException, InvalidHumanHeightException {
        City c1 = null, c2 = null;
        int a = 1;
        Integer A = a;
        Integer B = 1;
        System.out.println(A == B);
        Coordinates coord = new Coordinates(1, 2);
        //System.out.println("nextId " + Entities.City.City.nextId);
        Long id = new Long(1);
        Long nullId = null;
        c1 = new City(id, "c1", coord, 1, 1, 1, 1, Climate.HUMIDCONTINENTAL, StandardOfLiving.MEDIUM, new Human(1), new java.util.Date());
        System.out.println(c1.id);
        //System.out.println(Entities.City.City.nextId);
/*        try {
            //c1 = new Entities.City.City("c1", coord, 1, null);
            //c2 = new Entities.City.City("c2", null, 1, A);
        } catch (NullPointerException npe) {
            System.out.println("catch");
        }
*/        System.out.println(c1 + "\n" + c2);
        System.out.println(c1.coordinates);
        System.out.println(c1.creationDate);
        c1.getCreationDate().setTime(1000);
        System.out.println(c1.creationDate);
        System.out.println(c1.getClimate());
        Climate climate = c1.getClimate();
        climate = Climate.STEPPE;
        System.out.println(c1.getClimate());
/*
        Integer[] arr = new Integer[10];
        ArrayList<Integer> arrlist = new ArrayList<Integer>(arr);
*/
    }
}