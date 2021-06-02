package Entities.City;

import CSVTools.CSVReader;
import CSVTools.CSVRow;
import CSVTools.CSVToObj;
import CSVTools.ObjToCSV;
import Entities.Human;
import Exceptions.InvalidCityDataException;
import Exceptions.InvalidHumanHeightException;
import Exceptions.ObjectCreationFailedException;
import Entities.City.Parameters.Climate;
import Entities.City.Parameters.Coordinates;
import Entities.City.Parameters.StandardOfLiving;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CityCSVConverter implements CSVToObj<City>, ObjToCSV<City> {
    private static final String[] CITY_CSV_FIELDS = {"id",
                                                     "name",
                                                     "coordinates.x",
                                                     "coordinates.y",
                                                     "area",
                                                     "population",
                                                     "metersAboveSeaLevel",
                                                     "agglomeration",
                                                     "climate",
                                                     "standardOfLiving",
                                                     "governor.height",
                                                     "creationDate"};

    @Override
    public City createFromCSV(CSVRow csvRow) throws ObjectCreationFailedException {
        //Added: Exception when arguments in CSVRow are not enough
        String exMessage;
        Throwable exCause;
        if (csvRow.size() < 12) {
            exMessage = "Недостаточно параметров для задания города. Требуется параметров: 12, обнаружено: " + csvRow.size();
            throw new ObjectCreationFailedException(exMessage);
        }
        int field = 0;
        String[] cityData = csvRow.getValues();
        try{
            long id = Long.valueOf(cityData[field]);
            String name = cityData[++field];
            long coordX = Long.valueOf(cityData[++field]);
            double coordY = Double.valueOf(cityData[++field]);
            Coordinates coordinates = new Coordinates(coordX, coordY);
            float area = Float.valueOf(cityData[++field]);
            int population = Integer.valueOf(cityData[++field]);
            Float metersAboveSeaLevel = cityData[++field].isEmpty() ? null : Float.valueOf(cityData[field]);
            Integer agglomeration = cityData[++field].isEmpty() ? null : Integer.valueOf(cityData[field]);
            Climate climate = Enum.valueOf(Climate.class, cityData[++field]);
            StandardOfLiving standardOfLiving = Enum.valueOf(StandardOfLiving.class, cityData[++field]);
            Human governor = null;
            if (!cityData[++field].isEmpty()) {
                double governorHeight = Double.valueOf(cityData[field]);
                governor = new Human(governorHeight);
            }
            java.util.Date creationDate = (new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH)).parse(cityData[++field]);
            return new City(id, name, coordinates, area, population, metersAboveSeaLevel, agglomeration, climate, standardOfLiving, governor, creationDate);
        } catch (InvalidCityDataException | InvalidHumanHeightException e) {
            exMessage = "Указаны некорректные параметры города";
            exCause = e;
        } catch (IllegalArgumentException | ParseException e) {
            exMessage = "Ошибка при определении параметров города. Некорректное поле: " + CITY_CSV_FIELDS[field] + "; Значения поля: " + cityData[field];
            exCause = e;
        }
        throw new ObjectCreationFailedException(exMessage, exCause);
    }

    @Override
    public CSVRow serializeToCSV(City city) {
        String[] cityData = {city.getId().toString(),
                             city.getName(),
                             city.getCoordinates().getX().toString(),
                             Double.toString(city.getCoordinates().getY()),
                             Float.toString(city.getArea()),
                             city.getPopulation().toString(),
                             city.getMetersAboveSeaLevel() == null ?
                                     "" : city.getMetersAboveSeaLevel().toString(),
                             city.getAgglomeration() == null?
                                     "" : city.getAgglomeration().toString(),
                             city.getClimate().toString(),
                             city.getStandardOfLiving().toString(),
                             city.getGovernor() == null ?
                                     "" : Double.toString(city.getGovernor().getHeight()),
                             city.getCreationDate().toString()};
        return new CSVRow(cityData);
    }

    public static void main(String[] args) throws FileNotFoundException {
        CSVRow cr = new CSVRow(new String[]{"1", "2", "3"});
        String[] arr = cr.getValues();
        arr[0] = "0";
        for (String str : cr.getValues())
            System.out.println(str);
        String row = cr.toString();
        System.out.println(cr);

        CSVReader  csvReader = new CSVReader(new FileInputStream("Some City.txt"));
        try {
            CSVRow csvRow = csvReader.readCSVRow();
            CityCSVConverter cityCSVConverter = new CityCSVConverter();
            City city = cityCSVConverter.createFromCSV(csvRow);
            System.out.println(city.getId());
            System.out.println(city.getName());
            System.out.println(city.getCoordinates());
            System.out.println(city.getArea());
            System.out.println(city.getPopulation());
            System.out.println(city.getMetersAboveSeaLevel());
            System.out.println(city.getAgglomeration());
            System.out.println(city.getClimate());
            System.out.println(city.getStandardOfLiving());
            System.out.println(city.getGovernor());
            System.out.println(city.getCreationDate());
        } catch (ObjectCreationFailedException ocfe) {
            System.out.println(ocfe.getLocalizedMessage());
            System.out.println("Cause: " + ocfe.getCause());
        } catch (ParseException pe) {
            System.out.println(pe.getLocalizedMessage());
        }
    }
}
