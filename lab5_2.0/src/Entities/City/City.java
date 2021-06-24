package Entities.City;

import Entities.Human;
import Exceptions.*;
import Entities.City.Parameters.Climate;
import Entities.City.Parameters.Coordinates;
import Entities.City.Parameters.StandardOfLiving;

import java.util.Date;

/**
 * Class that represent cities
 */
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

    /**
     * Creates a new City with given parameters
     * @param id - id of the City. Must be greater than 0
     * @param name - name of the City. Can't be null or empty string
     * @param coordinates - coordinates of the City. Can't be null
     * @param area - area of the City. Must be greater than 0
     * @param population - population of the City. Must be greater than 0
     * @param metersAboveSeaLevel - height above sea level of the City
     * @param agglomeration - agglomeration of the City
     * @param climate - type of climate in the City. Can't be null
     * @param standardOfLiving - living standard in the City. Can't be null
     * @param governor - governor of the City
     * @throws InvalidCityDataException if any parameter has an invalid value
     * @throws NullPointerException if any parameter that can't be null is actually null
     */
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
            throws InvalidCityDataException, NullPointerException {
        this(id, name, coordinates, area, population, metersAboveSeaLevel,
             agglomeration, climate, standardOfLiving, governor, new java.util.Date());
    }

    /**
     * Creates a new City with given parameters
     * @param id - id of the City. Must be greater than 0
     * @param name - name of the City. Can't be null or empty string
     * @param coordinates - coordinates of the City. Can't be null
     * @param area - area of the City. Must be greater than 0
     * @param population - population of the City. Must be greater than 0
     * @param metersAboveSeaLevel - height above sea level of the City
     * @param agglomeration - agglomeration of the City
     * @param climate - type of climate in the City. Can't be null
     * @param standardOfLiving - living standard in the City. Can't be null
     * @param governor - governor of the City
     * @param creationDate - initialization date of this City. Can't be null
     * @throws InvalidCityDataException if any parameter has an invalid value
     * @throws NullPointerException if any parameter that can't be null is actually null
     */
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

    /**
     * Creates a copy of the specified City with new id.
     * @param id - id for newly created City
     * @param other - City to be copied
     */
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

    /**
     * Returns a string representation of this City
     * @return A string representation of this City
     */
    public String toString() { return name + " (id:" + id + ")"; }

    /**
     * Returns a detailed string representation of this City
     * @return A detailed string representation of this City
     */
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

    /**
     * Compares this City to the specified object. The result is true if and only
     * if the argument is not null and is a City object that represents a city with the
     * same size (population, area, agglomeration) as this object
     * (population.equals(other.population) && area == other.area &&
     * agglomeration.equals(other.agglomeration)).
     * @param obj - The object to compare this City against
     * @return true if the given object represents a City of equivalent size to this
     * City, false otherwise
     */
    public boolean equals(Object obj) {
        //Criteria of equality: population, area, agglomeration
        if (obj instanceof City) {
            City other = (City) obj;
            return population.equals(other.population) &&
                   area == other.area &&
                   agglomeration.equals(other.agglomeration);
        }
        return false;
    }

    /**
     * Returns a hash code for this City. The hash code for a City object is computed as
     * Integer.hashCode(population.hashCode() + Float.hashCode(area) + agglomeration.hashCode())
     * @return a hash code value for this object.
     */
    public int hashCode() {
        int hashSum = population.hashCode() + Float.hashCode(area) +
                      agglomeration.hashCode();
        return Integer.hashCode(hashSum);
    }

    /**
     * Compares this City to another. Cities are ordered first by population then by area
     * and the by agglomeration. The result is a positive integer if this City object
     * follows the argument City. The result is zero if the cities are equal; compareTo
     * returns 0 exactly when the equals(Object) method would return true. The result is a
     * negative integer if this City object precedes the argument City.
     * @param other - the City to be compared.
     * @return the value 0 if the argument City is equal to this City; a value less than 0
     * if this City is less than the City argument; and a value greater than 0 if this
     * City is greater than the City argument.
     */
    public int compareTo(City other) {
        //Comparison order: population -> area -> agglomeration
        int result = population.compareTo(other.population);
        if (result == 0) {
            result = Float.compare(area, other.area);
            if (result == 0)
                return agglomeration.compareTo(other.agglomeration);
        }
        return result;
    }

    /**
     * Returns id of this City
     * @return id of this City
     */
    public Long getId() { return id; }

    /**
     * Returns name of this City
     * @return name of this City
     */
    public String getName() { return name; }

    /**
     * Returns clone of coordinates of this City
     * @return clone of coordinates of this City
     */
    public Coordinates getCoordinates() { return coordinates.clone(); }

    /**
     * Returns area of this City
     * @return area of this City
     */
    public float getArea() { return area; }

    /**
     * Returns population of this City
     * @return population of this City
     */
    public Integer getPopulation() { return population; }

    /**
     * Returns height above sea level of this City
     * @return height above sea level of this City
     */
    public Float getMetersAboveSeaLevel() { return metersAboveSeaLevel; }

    /**
     * Returns agglomeration of this City
     * @return agglomeration of this City
     */
    public Integer getAgglomeration() { return agglomeration; }

    /**
     * Returns type of climate of this City
     * @return type of climate of this City
     */
    public Climate getClimate() { return climate; }

    /**
     * Returns living standard of this City
     * @return living standard of this City
     */
    public StandardOfLiving getStandardOfLiving() { return standardOfLiving; }

    /**
     * Returns clone of governor of this City
     * @return clone of governor of this City
     */
    public Human getGovernor() { return governor == null ? null : governor.clone(); }

    /**
     * Returns clone of initialization date of this City
     * @return clone of initialization date of this City
     */
    public Date getCreationDate() { return (Date) creationDate.clone(); }

    /**
     * Sets a new name of this City
     * @param name - a new name
     * @throws NullPointerException if name has null value
     * @throws InvalidCityNameException if name is an empty string
     */
    public void setName(String name) throws NullPointerException, InvalidCityNameException {
        if (name == null)
            throw new NullPointerException("Поле City.name не может иметь значение null.");
        else if (name.length() > 0)
            this.name = name;
        else
            throw new InvalidCityNameException();
    }

    /**
     * Sets new coordinates of this City
     * @param coordinates - new coordinates
     * @throws NullPointerException if coordinates is null
     */
    public void setCoordinates(Coordinates coordinates) throws NullPointerException {
        if (coordinates != null)
            this.coordinates = coordinates;
        else
            throw new NullPointerException("Поле City.coordinates не может иметь значение null.");
    }

    /**
     * Sets a new area of this City
     * @param area - a new area
     * @throws InvalidCityAreaException if area has value 0 or less
     */
    public void setArea(float area) throws InvalidCityAreaException {
        if (area > 0)
            this.area = area;
        else
            throw new InvalidCityAreaException();
    }

    /**
     * Sets a new population of this City
     * @param population - a new population
     * @throws InvalidCityPopulationException if area has value 0 or less
     */
    public void setPopulation(int population) throws InvalidCityPopulationException {
        if (population > 0)
            this.population = population;
        else
            throw new InvalidCityPopulationException();
    }

    /**
     * Sets a new height above sea level for this City
     * @param metersAboveSeaLevel - a new height above sea level
     */
    public void setMetersAboveSeaLevel(Float metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    /**
     * Sets a new agglomeration for this City
     * @param agglomeration - a new agglomeration
     */
    public void setAgglomeration(Integer agglomeration) {
        this.agglomeration = agglomeration;
    }

    /**
     * Sets a new type of climate in this City
     * @param climate - a new type of climate
     * @throws NullPointerException if climate has null value
     */
    public void setClimate(Climate climate) throws NullPointerException {
        if (climate != null)
            this.climate = climate;
        else
            throw new NullPointerException("Поле City.climate не может иметь значение null.");
    }

    /**
     * Sets a new living standard in this City
     * @param standardOfLiving - a new living standard
     * @throws NullPointerException if standardOfLiving has null value
     */
    public void setStandardOfLiving(StandardOfLiving standardOfLiving) throws NullPointerException {
        if (standardOfLiving != null)
            this.standardOfLiving = standardOfLiving;
        else
            throw new NullPointerException("Поле City.standardOfLiving не может иметь значение null.");
    }

    /**
     * Sets a new governor for this City
     * @param governor - a new governor
     */
    public void setGovernor(Human governor) { this.governor = governor; }

    /**
     * Returns the default City for testing purposes
     * @return the default City
     */
    public static City getDefaultCity() {
        //TODO: remove getDefaultCity
        Coordinates coord = new Coordinates(1, 2);
        return new City(1, "c1", coord, 1, 1, (float) 1, 1, Climate.HUMIDCONTINENTAL, StandardOfLiving.MEDIUM, new Human(1), new java.util.Date());

    }
}