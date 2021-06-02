package CityCollection;

import CSVTools.CSVReader;
import CSVTools.CSVRow;
import Entities.City.City;
import Entities.City.CityCSVConverter;
import Entities.City.Parameters.Climate;
import Entities.City.Parameters.Coordinates;
import Entities.City.Parameters.StandardOfLiving;
import Entities.Human;
import Exceptions.InvalidQuoteSequenceException;
import Exceptions.ObjectCreationFailedException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.rmi.AlreadyBoundException;
import java.text.ParseException;
import java.util.*;
//TODO:Все классы в программе должны быть задокументированы в формате javadoc.
//TODO:Программа должна корректно работать с неправильными данными (ошибки пользовательского ввода, отсутсвие прав доступа к файлу и т.п.).

//TODO: Maybe Logger?
public class CityCollectionManager {
    private CityBijectiveHashtable cityHashtable;
    private java.util.Date initDate;
    private Scanner sc;
    private String filename;
    private CityCSVConverter cityCSVConverter;
    private Charset charset = StandardCharsets.UTF_8;

    public CityCollectionManager() { this(new Scanner(System.in)); }

    public CityCollectionManager(Scanner sc) {
        this.cityHashtable = new CityBijectiveHashtable();
        this.initDate = new java.util.Date();
        this.sc = sc;
        this.cityCSVConverter = new CityCSVConverter();
    }

    /**
     * Clears CityBimap and fills it with Cities from the file.
     */
    protected int load(String filename) {
        int citiesLoaded = -1;
        try(FileInputStream fis = new FileInputStream(filename)) {
            this.filename = filename;
            cityHashtable.clear();
            City curCity;
            long curCityId = 0;
            CSVReader csvReader = new CSVReader(fis, charset);
            CSVRow csvRow, cityCSVRow;
            citiesLoaded = 0;
            while (csvReader.hasNext()) {
                csvRow = csvReader.readCSVRow();
                cityCSVRow = new CSVRow(Arrays.copyOfRange(csvRow.getValues(), 1, csvRow.size()));
                try {
                    curCity = cityCSVConverter.createFromCSV(cityCSVRow);
                    curCityId = curCity.getId();
                    cityHashtable.put(csvRow.getValues()[0], curCity);
                    citiesLoaded++;
                } catch (ObjectCreationFailedException ocfe) {
                    System.out.println("Не удалось создать город с ключом " +
                            csvRow.getValues()[0]);
                    System.out.println(ocfe.getMessage());
                    if (ocfe.getCause() != null)
                        System.out.println("Cause: " + ocfe.getCause().getMessage());
                    System.out.println();
                } catch (IllegalArgumentException iae) {
                    System.out.println("Не удалось добавить в коллекцию город с ключом " +
                            csvRow.getValues()[0] + " (id:" + curCityId + "):");
                    System.out.println(iae.getMessage());
                    System.out.println();
                }
            }
        } catch (ParseException pe) {
            System.out.println("Не удаётся прочитать файл в формате CSV.");
            System.out.println(pe.getMessage());
        } catch (FileNotFoundException fnfe) {
            System.out.print("Ошибка при открытии файла ");
            System.out.println(fnfe.getMessage());
            //TODO: Пополучать разные исключения после иземения ридера
        } catch (IOException ioe) {
            System.out.println("При чтении указанного файла возникла " +
                    "непредвиденная ошибка.");
            System.out.println(ioe.getMessage());
        }
        System.out.println("Загружено городов: " + citiesLoaded + ".");
        return citiesLoaded;
    }

    protected void save() throws NullPointerException {
        if (filename == null)
            throw new NullPointerException("Не указано имя файла для записи.");
        save(filename);
    }

    protected void save(String filename) {
        //TODO: test with permissions
        int citiesSaved = 0;
        try (OutputStreamWriter outStreamWriter = new OutputStreamWriter(new FileOutputStream(filename), charset)){
            this.filename = filename;
            List<String> csvRowStrArr = new ArrayList<>(cityHashtable.size());
            CSVRow cityCSVRow, keyCSVRow, keyCityCSVRow;
            for (Map.Entry<String, City> entry : cityHashtable.entrySet()) {
                cityCSVRow = cityCSVConverter.serializeToCSV(entry.getValue());
                keyCSVRow = new CSVRow(new String[]{entry.getKey()});
                try {
                    keyCityCSVRow = new CSVRow(keyCSVRow.toString() + CSVRow.delimiter() + cityCSVRow.toString());
                    csvRowStrArr.add(keyCityCSVRow.toString());
                } catch (InvalidQuoteSequenceException ignore) {} // Strings are received from CSVRow so they are supposed to be correct.
            }
            for (String csvRowStr : csvRowStrArr) {
                outStreamWriter.write(csvRowStr + System.lineSeparator());
                citiesSaved++;
            }
            System.out.println("Коллекция успешно сохранена в файл " + filename);
        } catch (FileNotFoundException fnfe) {
            System.out.print("Ошибка при записи в файл ");
            System.out.println(fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("При записи файла " + filename + " возникла " +
                    "непредвиденная ошибка.");
            System.out.println(ioe.getMessage());
        }
        System.out.println("Записано городов: " + citiesSaved);
    }

    protected City inputElement() {
        return inputElement(null);
    }

    protected City inputElement(City city) {
        String curLine;
        String name = null;
        while (name == null) {
            System.out.println("Введите название города:");
            name = sc.nextLine();
            if (name.equals("")) {
                name = null;
                System.out.println("Недопустимое имя города: имя города не может быть пустой строкой.");
            }
        }
        long coordX = 0;
        boolean correctInput = false;
        while (!correctInput) {
            System.out.println("Введите координату центра города по x (long):");
            if (sc.hasNextLong()) {
                coordX = sc.nextLong();
                sc.nextLine();
                correctInput = true;
            } else {
                while (sc.nextLine().replaceAll(" ", "").equals("")) {}
                System.out.println("Не удалось определить координату по x.");
                System.out.println("Координата по x должна быть задана числом типа long.");
            }
        }
        double coordY = 0;
        correctInput = false;
        while (!correctInput) {
            System.out.println("Введите координату центра города по y (double):");
            if (sc.hasNextDouble()) {
                coordY = sc.nextDouble();
                sc.nextLine();
                correctInput = true;
            } else {
                while (sc.nextLine().replaceAll(" ", "").equals("")) {}
                System.out.println("Не удалось определить координату по y.");
                System.out.println("Координата по y должна быть задана числом типа double.");
            }
        }
        Coordinates coordinates = new Coordinates(coordX, coordY);
        float area = 0;
        correctInput = false;
        while (!correctInput) {
            System.out.println("Введите площадь города (float):");
            if (sc.hasNextFloat()) {
                area = sc.nextFloat();
                sc.nextLine();
                if (area > 0)
                    correctInput = true;
                else
                    System.out.println("Недопустимое значение площади города: площадь города должна быть больше 0.");
            } else {
                while (sc.nextLine().replaceAll(" ", "").equals("")) {}
                System.out.println("Не удалось определить площадь города.");
                System.out.println("Площадь города должна быть задана числом типа float.");
            }
        }
        int population = 0;
        correctInput = false;
        while (!correctInput) {
            System.out.println("Введите население города (int):");
            if (sc.hasNextInt()) {
                population = sc.nextInt();
                sc.nextLine();
                if (population > 0)
                    correctInput = true;
                else
                    System.out.println("Недопустимое значение населения города: население города должно быть больше 0.");
            } else {
                while (sc.nextLine().replaceAll(" ", "").equals("")) {}
                System.out.println("Не удалось определить население города.");
                System.out.println("Население города должно быть задано числом типа int.");
            }
        }
        Float metersAboveSeaLevel = null;
        correctInput = false;
        while (!correctInput) {
            System.out.println("Следующий параметр можно не указывать, для этого оставьте " +
                    "строку пустой (без пробельных символов) и нажмите Enter.");
            System.out.println("Введите высоту города над уровнем моря (float):");
            curLine = sc.nextLine();
            if (curLine.equals("")) correctInput = true;
            else {
                try {
                    metersAboveSeaLevel = Float.valueOf(curLine.trim().split(" ")[0]);
                    correctInput = true;
                } catch (NumberFormatException nfe) {
                    System.out.println("Не удалось определить высоту города над уровнем моря.");
                    System.out.println("Высота города над уровнем моря должна быть задана числом типа float.");
                }
            }
        }
        Integer agglomeration = null;
        correctInput = false;
        while (!correctInput) {
            System.out.println("Следующий параметр можно не указывать, для этого оставьте " +
                    "строку пустой (без пробельных символов) и нажмите Enter.");
            System.out.println("Введите размер городской агломерации (int):");
            curLine = sc.nextLine();
            if (curLine.equals("")) correctInput = true;
            else {
                try {
                    agglomeration = Integer.valueOf(curLine.trim().split(" ")[0]);
                    correctInput = true;
                } catch (NumberFormatException nfe) {
                    System.out.println("Не удалось определить размер городской агломерации.");
                    System.out.println("Размер городской агломерации должен быть задан числом типа int.");
                }
            }
        }
        Climate climate = null;
        correctInput = false;
        Climate[] climateValues = Climate.values();
        String[] strClimateValuesArray = new String[climateValues.length];
        for (int i = 0; i < climateValues.length; i++)
            strClimateValuesArray[i] = climateValues[i].toString();
        String strClimateValues = String.join(", ", strClimateValuesArray);
        while (!correctInput) {
            System.out.println("Введите тип климата (Доступные варианты: " +
                             strClimateValues + "):");
            curLine = sc.nextLine().toUpperCase();
            try {
                climate = Climate.valueOf(curLine.trim().split(" ")[0]);
                correctInput = true;
            } catch (IllegalArgumentException iae) {
                System.out.println("Не удалось определить тип климата.");
            }
        }
        StandardOfLiving standardOfLiving = null;
        correctInput = false;
        StandardOfLiving[] standardOfLivingValues = StandardOfLiving.values();
        String[] strStandardOfLivingArray = new String[standardOfLivingValues.length];
        for (int i = 0; i < standardOfLivingValues.length; i++)
            strStandardOfLivingArray[i] = standardOfLivingValues[i].toString();
        String strStandardOfLivingValues = String.join(", ", strStandardOfLivingArray);
        while (!correctInput) {
            System.out.println("Введите уровень жизни населения (Доступные варианты: " +
                    strStandardOfLivingValues + "):");
            curLine = sc.nextLine().toUpperCase();
            try {
                standardOfLiving = StandardOfLiving.valueOf(curLine.trim().split(" ")[0]);
                correctInput = true;
            } catch (IllegalArgumentException iae) {
                System.out.println("Не удалось определить уровень жизни населения.");
            }
        }
        double governorHeight = 0;
        Human governor = null;
        correctInput = false;
        while (!correctInput) {
            System.out.println("Введите рост губернатора города (double), " +
                    "если в городе нет губернатора оставьте строку пустой " +
                    "(без пробельных символов) и нажмите Enter:");
            curLine = sc.nextLine();
            if (curLine.equals("")) correctInput = true;
            else {
                try {
                    governorHeight = Double.valueOf(curLine.trim().split(" ")[0]);
                    if (governorHeight > 0) {
                        governor = new Human(governorHeight);
                        correctInput = true;
                    } else
                        System.out.println("Недопустимое значение человеческого роста: " +
                                "рост должен быть больше 0.");
                } catch (NumberFormatException nfe) {
                    System.out.println("Не удалось определить рост губернатора города.");
                    System.out.println("Человеческий рост должен быть задан числом типа double.");
                }
            }
        }
        if (city == null)
            return new City(1, name, coordinates, area, population, metersAboveSeaLevel, agglomeration, climate, standardOfLiving, governor, new Date());
        city.setName(name);
        city.setCoordinates(coordinates);
        city.setArea(area);
        city.setPopulation(population);
        city.setMetersAboveSeaLevel(metersAboveSeaLevel);
        city.setAgglomeration(agglomeration);
        city.setClimate(climate);
        city.setStandardOfLiving(standardOfLiving);
        city.setGovernor(governor);
        return city;
    }

    public void work() {
        String[] command = {};
        City city;
        String key;
        while (true) {
            command = sc.nextLine().split(" ");
            while (command.length < 1)
                command = sc.nextLine().split(" ");
            command[0] = command[0].toLowerCase();
            switch (command[0]) {
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
                    System.out.println("Collection info:\n\tType:\n\t\t" + cityHashtable.getClass().getSimpleName() +
                            "<" + String.class.getSimpleName() +
                            ", " + City.class.getSimpleName() +
                            ">\n\tInitialization time:\n\t\t" + initDate +
                            "\n\tSize:\n\t\t" + cityHashtable.size() + " element" + ((cityHashtable.size() == 1)? "" : "s"));
                    break;
                case "show":
                    System.out.println(cityHashtable);
                    break;
                case "insert":
                    key = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
                    city = inputElement();
                    cityHashtable.putWithUniqId(key, city);
                    System.out.println("В коллекцию по ключу " + key + " добавлен город " +
                                       cityHashtable.get(key).getFullDescription());
                    break;
                case "update":
                    if (command.length > 1) {
                        try {
                            long id = Long.valueOf(command[1]);
                            key = cityHashtable.getKeyForId(id);
                            if (key == null)
                                System.out.println("В коллекции нет города с заданным id.");
                            else {
                                city = cityHashtable.get(key);
                                inputElement(city);
                                System.out.println("Данные города " + city + " обновлены.");
                                System.out.println("Ключ: " + key +
                                        "\nНовые данные города " +
                                        cityHashtable.get(key).getFullDescription());
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("Не удалось определить id (long).");
                            System.out.println(nfe.getMessage());
                        }
                    } else
                        System.out.println("Команда update принимает обязательный аргумент id (long).");
                    break;
                case "remove_key":
                    key = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
                    city = cityHashtable.remove(key);
                    if (city != null)
                        System.out.println("Из коллекции по ключу " + key + " удален " +
                                city.getFullDescription());
                    else
                        System.out.println("В коллекции нет соответствия для ключа " + key + ".");
                    break;
                case "clear":
                    cityHashtable.clear();
                    System.out.println("Все города из коллекции удалены.");
                    break;
                case "save":
                    if (filename == null) {
                        System.out.println("Не указано имя файла для записи.");
                        System.out.println("Воспользуйтесь командой save_as с указанием имени файла.");
                    } else
                        save();
                    break;
                case "save_as":
                    if (command.length > 1)
                        save(String.join(" ", Arrays.copyOfRange(command, 1, command.length)));
                    else
                        System.out.println("Команда save_as принимает обязательный аргумент file_name (String).");
                    break;
                case "load":
                    if (command.length > 1)
                        load(String.join(" ", Arrays.copyOfRange(command, 1, command.length)));
                    else
                        System.out.println("Команда load принимает обязательный аргумент file_name (String).");
                    break;
                case "exit":
                    System.exit(0);
                //TODO: remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
                //TODO: remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
                //TODO: replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого
                //TODO: sum_of_agglomeration : вывести сумму значений поля agglomeration для всех элементов коллекции
                //TODO: max_by_standard_of_living : вывести любой объект из коллекции, значение поля standardOfLiving которого является максимальным
                //TODO: filter_less_than_climate climate : вывести элементы, значение поля climate которых меньше заданного
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
        }
    }

    public static void main(String[] args) throws AlreadyBoundException, FileNotFoundException, ParseException {
        CityCollectionManager ccm = new CityCollectionManager();
        if (args.length > 0 && ccm.load(args[0]) < 0)
            System.out.println("Воспользуйтесь командой load, чтобы загрузить коллекцию.");
        ccm.work();
        /*
        String key0 = ccm.cityHashtable.keys().nextElement();
        Coordinates coord = new Coordinates(1, 2);
        City c1 = new City(1, "c1", coord, 1, 1, 1, 1, Climate.HUMIDCONTINENTAL, StandardOfLiving.MEDIUM, new Human(1), new java.util.Date());
        ccm.cityHashtable.add("c1", c1);
        System.out.println(ccm.cityHashtable);
        c1.setGovernor(new Human(228));
        System.out.println(ccm.cityHashtable.get("c1").getGovernor());


        for (String key : ccm.cityHashtable.keySet())
            System.out.println(key + ": " + ccm.cityHashtable.get(key));
            */
    }
}
