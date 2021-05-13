package Entities.City;

import CSVTools.CSVReader;
import CSVTools.CSVRow;
import CSVTools.CSVToObj;
import Entities.Human;
import Exceptions.InvalidCityDataException;
import Exceptions.InvalidHumanHeightException;
import Exceptions.ObjectCreationFailedException;
import Parameters.Climate;
import Parameters.Coordinates;
import Parameters.StandardOfLiving;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

public class CityCreator implements CSVToObj<City> {
    public City createFromCSV(CSVRow csvRow) throws ObjectCreationFailedException {
        String exMessage = "%s. Возможно файл с данными был повреждён или изменён.";
        String brokenData;
        Throwable exCause;
        try{
            String[] cityData = csvRow.getValues();
            long id = Long.valueOf(cityData[0]);
            String name = cityData[1];
            long coordX = Long.valueOf(cityData[2]);
            double coordY = Double.valueOf(cityData[3]);
            Coordinates coordinates = new Coordinates(coordX, coordY);
            float area = Float.valueOf(cityData[4]);
            int population = Integer.valueOf(cityData[5]);
            float metersAboveSeaLevel = Float.valueOf(cityData[6]);
            int agglomeration = Integer.valueOf(cityData[7]);
            Climate climate = Enum.valueOf(Climate.class, cityData[8]);
            StandardOfLiving standardOfLiving = Enum.valueOf(StandardOfLiving.class, cityData[9]);
            double governorHeight = Double.valueOf(cityData[10]);
            Human governor;
            try {
                governor = new Human(governorHeight);
                java.util.Date creationDate = (new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH)).parse(cityData[11]);
                return new City(id, name, coordinates, area, population, metersAboveSeaLevel, agglomeration, climate, standardOfLiving, governor, creationDate);
            } catch(InvalidHumanHeightException ihhe) {
                brokenData = "высоту губернатора";
                exCause = ihhe;
            } catch(ParseException pe) {
                brokenData = "дату создания";
                exCause = pe;
            } catch (InvalidCityDataException icde) {
                brokenData = "параметры";
                exCause = icde;
            }
            exMessage = String.format(exMessage, "Не удалось корректно определить %s города %s (id: %d)");
            exMessage = String.format(exMessage, brokenData, name, id);
        } catch (IllegalArgumentException iae) {
            exMessage = String.format(exMessage, "Ошибка при определении параметров города");
            exCause = iae;
        }
        throw new ObjectCreationFailedException(exMessage, exCause);
    }

    public static void main(String[] args) throws FileNotFoundException {
        CSVRow cr = new CSVRow(new String[]{"1", "2", "3"});
        String[] arr = cr.getValues();
        arr[0] = "0";
        for (String str : cr.getValues())
            System.out.println(str);
        String row = cr.toString();
        System.out.println(cr);

        CSVReader  csvReader = new CSVReader(new Scanner(new File("Some Cit.txt")));
        try {
            CSVRow csvRow = csvReader.readCSVRow();
            CityCreator cityCreator = new CityCreator();
            City city = cityCreator.createFromCSV(csvRow);
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
