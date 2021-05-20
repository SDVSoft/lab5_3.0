package CityCollection;

import CSVTools.CSVReader;
import CSVTools.CSVRow;
import Entities.City.City;
import Entities.City.CityCreator;
import Entities.Human;
import Exceptions.ObjectCreationFailedException;
import Parameters.Climate;
import Parameters.Coordinates;
import Parameters.StandardOfLiving;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.rmi.AlreadyBoundException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Scanner;
//TODO:При запуске приложения коллекция должна автоматически заполняться значениями из файла.
//TODO:Имя файла должно передаваться программе с помощью: переменная окружения.
//TODO:Данные должны храниться в файле в формате csv
//TODO:Чтение данных из файла необходимо реализовать с помощью класса java.io.BufferedInputStream
//TODO:Запись данных в файл необходимо реализовать с помощью класса java.io.OutputStreamWriter
//TODO:Все классы в программе должны быть задокументированы в формате javadoc.
//TODO:Программа должна корректно работать с неправильными данными (ошибки пользовательского ввода, отсутсвие прав доступа к файлу и т.п.).

//TODO: Maybe Logger?
public class CityCollectionManager {
    private CityBimap cityCollection;
    private java.util.Date initDate;

    public CityCollectionManager() {
        this.cityCollection = new CityBimap();
        this.initDate = new java.util.Date();
    }

    /**
     * Clears CityBimap and fills it with Cities from the file.
     */
    public int load(String filename) throws FileNotFoundException {
        int citiesLoaded = 0;
        Scanner startColSc = new Scanner(new File(filename));
        cityCollection.clear();
        try {
            CityCreator cityCreator = new CityCreator();
            CSVRow csvRow, cityCSVRow;
            while (startColSc.hasNext()) {
                csvRow = CSVReader.readCSVRow(startColSc);
                cityCSVRow = new CSVRow(Arrays.copyOfRange(csvRow.getValues(), 1, csvRow.getSize()));
                cityCollection.add(csvRow.getValues()[0], cityCreator.createFromCSV(cityCSVRow));
                citiesLoaded++;
            }
        } catch (ParseException | ObjectCreationFailedException | AlreadyBoundException exception) {
            System.out.println("При загрузке коллекции возникла ошибка.");
            System.out.println(exception.getMessage());
        }
        return citiesLoaded;
    }

    public void work() { work(System.in); }

    public void work(InputStream is) {
        Scanner sc = new Scanner(is);
        String token;
        while (true) {
            token = sc.next();
            switch (token) {
                case "help":
                    try{
                        Scanner cmdHelpSc = new Scanner(new File("./Command help.txt"));
                        while(cmdHelpSc.hasNext()) {
                            System.out.println(cmdHelpSc.nextLine());
                        }
                    }
                    catch (FileNotFoundException e) {
                        System.out.println("Не найден файл справки по доступным командам.");
                    }
                    break;
                case "info":
                    System.out.println("Collection info:\n\tType:\n\t\t" + cityCollection.getClass().getSimpleName() +
                            "<" + String.class.getSimpleName() +
                            ", " + City.class.getSimpleName() +
                            ">\n\tInitialization time:\n\t\t" + initDate +
                            "\n\tSize:\n\t\t" + cityCollection.size() + " element" + ((cityCollection.size() == 1)? "" : "s"));
                    break;
                case "show":
                    System.out.println(cityCollection);
                    break;
                case "load":
                    //TODO:continue
                    //TODO:add this command to Command help.txt
                case "exit":
                    System.exit(0);
/*
                case "remove":
                    hp_queue.remove(HumanCreator.JSONToHuman(jr.readJSONRow()));
                    break;
                case "clear":
                    hp_queue.clear();
                    break;
                case "info":
                    System.out.println(hp_queue.getInfo());
                    break;
                case "show":
                    for (Entities.Human human : hp_queue.toArray())
                        System.out.println(HumanDescriptor.HumanToJSON(human));
                    break;
                case "add":
                    hp_queue.add(HumanCreator.JSONToHuman(jr.readJSONRow()));
                    break;
                case "remove_greater":
                    hp_queue.removeGreater(HumanCreator.JSONToHuman(jr.readJSONRow()));
                    break;
                case "import":
                    boolean end_reached = false;
                    String path = sc.nextLine();
                    char[] path_arr = path.toCharArray();
                    int begin = -1, end = -1;
                    for (int i = 0; i < path.length() && !end_reached; i++) {
                        if (path_arr[i] == '\"') {
                            if (begin == -1) begin = i + 1;
                            else {
                                end = i;
                                end_reached = true;
                            }
                        }
                    }
                    path = path.substring(begin, end);

                    try {
                        hp_queue.download(path);
                    } catch (FileNotFoundException e) {
                        System.out.println("Неверно указан путь к файлу.");
                    }
                    break;
                case "exit":
                    System.exit(0);
            */
            }
            sc.nextLine();
        }
    }

    public static void main(String[] args) throws AlreadyBoundException {
        CityCollectionManager ccm = new CityCollectionManager();
        if (args.length > 0) {
            try {
                System.out.println("Загружено городов: " + ccm.load(args[0]) + ".");
            } catch (FileNotFoundException fnfe) {
                System.out.println("Не удалось найти указанный файл. " +
                        "Воспользуйтесь командой load, чтобы загрузить коллекцию.");
            }
        }
        //ccm.work();
        /*
        String key0 = ccm.cityCollection.keys().nextElement();
        Coordinates coord = new Coordinates(1, 2);
        City c1 = new City(1, "c1", coord, 1, 1, 1, 1, Climate.HUMIDCONTINENTAL, StandardOfLiving.MEDIUM, new Human(1), new java.util.Date());
        ccm.cityCollection.add("c1", c1);
        System.out.println(ccm.cityCollection);
        c1.setGovernor(new Human(228));
        System.out.println(ccm.cityCollection.get("c1").getGovernor());


        for (String key : ccm.cityCollection.keySet())
            System.out.println(key + ": " + ccm.cityCollection.get(key));
            */
    }
}
