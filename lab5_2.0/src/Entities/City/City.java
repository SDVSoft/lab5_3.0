package Entities.City;

import Entities.Human;
import Exceptions.*;
import Entities.City.Parameters.Climate;
import Entities.City.Parameters.Coordinates;
import Entities.City.Parameters.StandardOfLiving;

import java.util.Date;

public class City implements Comparable<City> {
    private final Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
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

    public City(long id,
                String name,
                Entities.City.Parameters.Coordinates coordinates,
                float area,
                int population,
                Float metersAboveSeaLevel,
                Integer agglomeration,
                Entities.City.Parameters.Climate climate,
                Entities.City.Parameters.StandardOfLiving standardOfLiving,
                Entities.Human governor)
            throws InvalidCityDataException {
        this(id, name, coordinates, area, population, metersAboveSeaLevel,
             agglomeration, climate, standardOfLiving, governor, new java.util.Date());
    }

    public City(long id,
                String name,
                Coordinates coordinates,
                float area,
                int population,
                Float metersAboveSeaLevel,
                Integer agglomeration,
                Climate climate,
                StandardOfLiving standardOfLiving,
                Human governor,
                java.util.Date creationDate)
            throws InvalidCityDataException, NullPointerException {
        if (id > 0)
            this.id = id;
        else
            throw new InvalidCityIdException();
        setName(name);
        setCoordinates(coordinates);
        setArea(area);
        setPopulation(population);
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.agglomeration = agglomeration;
        setClimate(climate);
        setStandardOfLiving(standardOfLiving);
        this.governor = governor;
        if (creationDate != null)
            this.creationDate = creationDate;
        else
            throw new NullPointerException("Поле City.creationDate не может иметь значение null.");
    }

    public City(long id, City other) {
        if (id > 0)
            this.id = id;
        else
            throw new InvalidCityIdException();
        this.name = other.name;
        this.coordinates = other.coordinates.clone();
        this.area = other.area;
        this.population = other.population;
        this.metersAboveSeaLevel = other.metersAboveSeaLevel;
        this.climate = other.climate;
        this.standardOfLiving = other.standardOfLiving;
        if (other.governor != null)
            this.governor = other.governor.clone();
        this.creationDate = (Date) other.creationDate.clone();
    }

    public String toString() { return name + " (id:" + id + ")"; }

    public String getFullDescription() {
        StringBuilder fullDescription = new StringBuilder();
        fullDescription.append(toString());
        fullDescription.append("\n\tКоординаты центра:\n\t\t").append(coordinates);
        fullDescription.append("\n\tПлощадь:\n\t\t").append(area);
        fullDescription.append("\n\tНаселение:\n\t\t").append(population);
        if (metersAboveSeaLevel != null)
            fullDescription.append("\n\tВысота на уровнем моря:\n\t\t").append(metersAboveSeaLevel);
        if (agglomeration != null)
            fullDescription.append("\n\tРазмер городской агломерации:\n\t\t").append(agglomeration);
        fullDescription.append("\n\tТип климата:\n\t\t").append(climate);
        fullDescription.append("\n\tУровень жизни населения:\n\t\t").append(standardOfLiving);
        if (governor != null)
            fullDescription.append("\n\tГубернатор:\n\t\t").append(governor);
        return fullDescription.toString();
    }

    public boolean equals(Object obj) {
        //Criteria of equality: population, area, agglomeration and coordinates
        if (obj instanceof City) {
            City other = (City) obj;
            return coordinates.equals(other.coordinates) &&
                   population.equals(other.population) &&
                   area == other.area &&
                   agglomeration.equals(other.agglomeration);
        }
        return false;
    }

    public int hashCode() {
        int hashSum = population.hashCode() + Float.hashCode(area) +
                      agglomeration.hashCode() + coordinates.hashCode();
        return Integer.hashCode(hashSum);
    }

    public int compareTo(City other) {
        //Comparison order: population -> area -> agglomeration -> coordinates
        int result = population.compareTo(other.population);
        if (result == 0) {
            result = Float.compare(area, other.area);
            if (result == 0) {
                result = agglomeration.compareTo(other.agglomeration);
                if (result == 0)
                    return coordinates.compareTo(other.coordinates);
            }
        }
        return result;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public Coordinates getCoordinates() { return coordinates.clone(); }

    public float getArea() { return area; }

    public Integer getPopulation() { return population; }

    public Float getMetersAboveSeaLevel() { return metersAboveSeaLevel; }

    public Integer getAgglomeration() { return agglomeration; }

    public Climate getClimate() { return climate; }

    public StandardOfLiving getStandardOfLiving() { return standardOfLiving; }

    public Human getGovernor() { return governor == null ? null : governor.clone(); }

    public Date getCreationDate() { return (Date) creationDate.clone(); }

    public void setName(String name) throws NullPointerException, InvalidCityNameException {
        if (name == null)
            throw new NullPointerException("Поле City.name не может иметь значение null.");
        else if (name.length() > 0)
            this.name = name;
        else
            throw new InvalidCityNameException();
    }

    public void setCoordinates(Coordinates coordinates) throws NullPointerException {
        if (coordinates != null)
            this.coordinates = coordinates;
        else
            throw new NullPointerException("Поле City.coordinates не может иметь значение null.");
    }

    public void setArea(float area) throws InvalidCityAreaException {
        if (area > 0)
            this.area = area;
        else
            throw new InvalidCityAreaException();
    }

    public void setPopulation(int population) throws InvalidCityPopulationException {
        if (population > 0)
            this.population = population;
        else
            throw new InvalidCityPopulationException();
    }

    public void setMetersAboveSeaLevel(Float metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public void setAgglomeration(Integer agglomeration) {
        this.agglomeration = agglomeration;
    }

    public void setClimate(Climate climate) throws NullPointerException {
        if (climate != null)
            this.climate = climate;
        else
            throw new NullPointerException("Поле City.climate не может иметь значение null.");
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) throws NullPointerException {
        if (standardOfLiving != null)
            this.standardOfLiving = standardOfLiving;
        else
            throw new NullPointerException("Поле City.standardOfLiving не может иметь значение null.");
    }

    public void setGovernor(Human governor) { this.governor = governor; }

    public static City getDefaultCity() {
        //TODO: remove getDefaultCity
        Coordinates coord = new Coordinates(1, 2);
        return new City(1, "c1", coord, 1, 1, (float) 1, 1, Climate.HUMIDCONTINENTAL, StandardOfLiving.MEDIUM, new Human(1), new java.util.Date());

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
        c1 = new City(id, "c1", coord, 1, 1, (float) 1, 1, Climate.HUMIDCONTINENTAL, StandardOfLiving.MEDIUM, new Human(1), new java.util.Date());
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
        c1.setClimate(null);
        System.out.println(c1.getClimate());
/*
        Integer[] arr = new Integer[10];
        ArrayList<Integer> arrlist = new ArrayList<Integer>(arr);
*/
    }
}