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
import java.text.ParseException;
import java.util.*;
//TODO:Программа должна корректно работать с неправильными данными (ошибки пользовательского ввода, отсутсвие прав доступа к файлу и т.п.).

/**
 * CityCollectionManager is a class that provides the ability to interact with a
 * collection of Cities interactively through the command line.
 */
public class CityCollectionManager {
    private CityBijectiveHashtable cityHashtable;
    private java.util.Date initDate;
    private Scanner sc;
    private Scanner mainSc;
    private String filename;
    private CityCSVConverter cityCSVConverter;
    private Charset charset = StandardCharsets.UTF_8;
    private boolean quiet;
    private boolean scriptExecution;
    private boolean quietScriptOut;
    private Exception criticalException;
    private int nonCriticalLoadExceptions = 0;

    /**
     * Initializes a new CityCollectionManager that will be controlled via the "standard"
     * input stream.
     */
    public CityCollectionManager() { this(new Scanner(System.in)); }

    /**
     * Initializes a new CityCollectionManager with the specified Scanner
     * @param sc - a Scanner to use.
     */
    public CityCollectionManager(Scanner sc) {
        this.cityHashtable = new CityBijectiveHashtable();
        this.initDate = new java.util.Date();
        this.mainSc = this.sc = sc;
        this.cityCSVConverter = new CityCSVConverter();
        this.quiet = false;
        this.scriptExecution = false;
    }

    /**
     * Clears CityHashtable and fills it with Cities from the file.
     */
    protected int load(String filename) {
        int citiesLoaded = -1;
        nonCriticalLoadExceptions = 0;
        File loadFile = new File(filename);
        if (!loadFile.isFile()) {
            criticalException = new IOException(filename +
                    " is not a normal file. Loading is forbidden.");
            System.out.println(criticalException.getMessage());
        } else {
            try (FileInputStream fis = new FileInputStream(loadFile)) {
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
                        nonCriticalLoadExceptions++;
                        System.out.println("Не удалось создать город с ключом " +
                                csvRow.getValues()[0]);
                        System.out.println(ocfe.getMessage());
                        if (ocfe.getCause() != null)
                            System.out.println("Cause: " + ocfe.getCause().getMessage());
                        System.out.println();
                    } catch (IllegalArgumentException iae) {
                        nonCriticalLoadExceptions++;
                        System.out.println("Не удалось добавить в коллекцию город с ключом " +
                                csvRow.getValues()[0] + " (id:" + curCityId + "):");
                        System.out.println(iae.getMessage());
                        System.out.println();
                    }
                }
            } catch (ParseException pe) {
                System.out.println("Не удаётся прочитать файл в формате CSV.");
                System.out.println(pe.getMessage());
                criticalException = pe;
            } catch (FileNotFoundException fnfe) {
                System.out.print("Ошибка при открытии файла ");
                System.out.println(fnfe.getMessage());
                criticalException = fnfe;
            } catch (IOException ioe) {
                System.out.println("При чтении указанного файла возникла " +
                        "непредвиденная ошибка.");
                System.out.println(ioe.getMessage());
                criticalException = ioe;
            }
        }
        String resultMsg = "Загружено городов: " + citiesLoaded + ".";
        if (nonCriticalLoadExceptions > 0)
            System.out.println(resultMsg);
        else
            quietPrintln(resultMsg);
        return citiesLoaded;
    }

    /**
     * Saves current collection to the file that was previously specified in
     * save(String filename) or in load(String filename)
     * @throws NullPointerException if the file wasn't specified yet
     */
    protected void save() throws NullPointerException {
        if (filename == null)
            throw new NullPointerException("Не указано имя файла для записи.");
        save(filename);
    }

    /**
     * Saves current collection to the specified file
     * @param filename - the name of the file to store the collection
     */
    protected void save(String filename) {
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
            quietPrintln("Коллекция успешно сохранена в файл " + filename);
        } catch (FileNotFoundException fnfe) {
            System.out.print("Ошибка при записи в файл ");
            System.out.println(fnfe.getMessage());
            criticalException = fnfe;
        } catch (IOException ioe) {
            System.out.println("При записи файла " + filename + " возникла " +
                    "непредвиденная ошибка.");
            System.out.println(ioe.getMessage());
            criticalException = ioe;
        }
        quietPrintln("Записано городов: " + citiesSaved);
    }

    private String ObjectsToStr(Object[] objs) {
        String[] strEnumValuesArray = new String[objs.length];
        for (int i = 0; i < objs.length; i++)
            strEnumValuesArray[i] = objs[i].toString();
        return String.join(", ", strEnumValuesArray);
    }

    /**
     * Creates a new City from given data
     * @return City that was created
     */
    protected City inputElement() {
        return inputElement(null);
    }

    /**
     * Sets new data to the specified City
     * @param city - the City to update data for
     * @return the specified City  with updated data or a new City with given data if the
     * specified City object was null
     */
    protected City inputElement(City city) {
        String curLine;
        String name = null;
        String exceptionMsg = null;
        while (name == null) {
            quietPrintln("Введите название города:");
            name = sc.nextLine();
            if (name.equals("")) {
                name = null;
                exceptionMsg = "Недопустимое имя города: имя города не может быть пустой строкой.";
                System.out.println(exceptionMsg);
                if (scriptExecution) {
                    criticalException = new IllegalArgumentException(exceptionMsg);
                    return null;
                }
            }
        }
        long coordX = 0;
        boolean correctInput = false;
        while (!correctInput) {
            quietPrintln("Введите координату центра города по x (long):");
            if (sc.hasNextLong()) {
                coordX = sc.nextLong();
                sc.nextLine();
                correctInput = true;
            } else {
                while (sc.nextLine().replaceAll(" ", "").equals("")) {}
                exceptionMsg = "Не удалось определить координату по x. " +
                               "Координата по x должна быть задана числом типа long.";
                System.out.println(exceptionMsg);
                if (scriptExecution) {
                    criticalException = new IllegalArgumentException(exceptionMsg);
                    return null;
                }
            }
        }
        double coordY = 0;
        correctInput = false;
        while (!correctInput) {
            quietPrintln("Введите координату центра города по y (double):");
            if (sc.hasNextDouble()) {
                coordY = sc.nextDouble();
                sc.nextLine();
                correctInput = true;
            } else {
                while (sc.nextLine().replaceAll(" ", "").equals("")) {}
                exceptionMsg = "Не удалось определить координату по y. " +
                               "Координата по y должна быть задана числом типа double.";
                System.out.println(exceptionMsg);
                if (scriptExecution) {
                    criticalException = new IllegalArgumentException(exceptionMsg);
                    return null;
                }
            }
        }
        Coordinates coordinates = new Coordinates(coordX, coordY);
        float area = 0;
        correctInput = false;
        exceptionMsg = null;
        while (!correctInput) {
            quietPrintln("Введите площадь города (float):");
            if (sc.hasNextFloat()) {
                area = sc.nextFloat();
                sc.nextLine();
                if (area > 0)
                    correctInput = true;
                else
                    exceptionMsg = "Недопустимое значение площади города: площадь города должна быть больше 0.";
            } else {
                while (sc.nextLine().replaceAll(" ", "").equals("")) {}
                exceptionMsg = "Не удалось определить площадь города.\n" +
                               "Площадь города должна быть задана числом типа float.";
            }
            if (!correctInput) {
                System.out.println(exceptionMsg);
                if (scriptExecution) {
                    criticalException = new IllegalArgumentException(exceptionMsg);
                    return null;
                }
            }
        }
        int population = 0;
        correctInput = false;
        exceptionMsg = null;
        while (!correctInput) {
            quietPrintln("Введите население города (int):");
            if (sc.hasNextInt()) {
                population = sc.nextInt();
                sc.nextLine();
                if (population > 0)
                    correctInput = true;
                else
                    exceptionMsg = "Недопустимое значение населения города: население города должно быть больше 0.";
            } else {
                while (sc.nextLine().replaceAll(" ", "").equals("")) {}
                exceptionMsg = "Не удалось определить население города.\n" +
                               "Население города должно быть задано числом типа int.";
            }
            if (!correctInput) {
                System.out.println(exceptionMsg);
                if (scriptExecution) {
                    criticalException = new IllegalArgumentException(exceptionMsg);
                    return null;
                }
            }
        }
        Float metersAboveSeaLevel = null;
        correctInput = false;
        while (!correctInput) {
            quietPrintln("Следующий параметр можно не указывать, для этого оставьте " +
                    "строку пустой (без пробельных символов) и нажмите Enter.");
            quietPrintln("Введите высоту города над уровнем моря (float):");
            curLine = sc.nextLine();
            if (curLine.equals("")) correctInput = true;
            else {
                try {
                    metersAboveSeaLevel = Float.valueOf(curLine.trim().split(" ")[0]);
                    correctInput = true;
                } catch (NumberFormatException nfe) {
                    exceptionMsg = "Не удалось определить высоту города над уровнем моря." +
                                   "Высота города над уровнем моря должна быть задана числом типа float.";
                    System.out.println(exceptionMsg);
                    if (scriptExecution) {
                        criticalException = new IllegalArgumentException(exceptionMsg);
                        return null;
                    }
                }
            }
        }
        Integer agglomeration = null;
        correctInput = false;
        while (!correctInput) {
            quietPrintln("Следующий параметр можно не указывать, для этого оставьте " +
                    "строку пустой (без пробельных символов) и нажмите Enter.");
            quietPrintln("Введите размер городской агломерации (int):");
            curLine = sc.nextLine();
            if (curLine.equals("")) correctInput = true;
            else {
                try {
                    agglomeration = Integer.valueOf(curLine.trim().split(" ")[0]);
                    correctInput = true;
                } catch (NumberFormatException nfe) {
                    exceptionMsg = "Не удалось определить размер городской агломерации." +
                                   "Размер городской агломерации должен быть задан числом типа int.";
                    System.out.println(exceptionMsg);
                    if (scriptExecution) {
                        criticalException = new IllegalArgumentException(exceptionMsg);
                        return null;
                    }
                }
            }
        }
        Climate climate = null;
        correctInput = false;
        String strClimateValues = ObjectsToStr(Climate.values());
        while (!correctInput) {
            quietPrintln("Введите тип климата (Доступные варианты: " +
                             strClimateValues + "):");
            curLine = sc.nextLine().toUpperCase();
            try {
                climate = Climate.valueOf(curLine.trim().split(" ")[0]);
                correctInput = true;
            } catch (IllegalArgumentException iae) {
                exceptionMsg = "Не удалось определить тип климата.\n" + iae.getMessage();
                System.out.println(exceptionMsg);
                if (scriptExecution) {
                    criticalException = new IllegalArgumentException(exceptionMsg);
                    return null;
                }
            }
        }
        StandardOfLiving standardOfLiving = null;
        correctInput = false;
        String strStandardOfLivingValues = ObjectsToStr(StandardOfLiving.values());
        while (!correctInput) {
            quietPrintln("Введите уровень жизни населения (Доступные варианты: " +
                    strStandardOfLivingValues + "):");
            curLine = sc.nextLine().toUpperCase();
            try {
                standardOfLiving = StandardOfLiving.valueOf(curLine.trim().split(" ")[0]);
                correctInput = true;
            } catch (IllegalArgumentException iae) {
                exceptionMsg = "Не удалось определить уровень жизни населения.\n" + iae.getMessage();
                System.out.println(exceptionMsg);
                if (scriptExecution) {
                    criticalException = new IllegalArgumentException(exceptionMsg);
                    return null;
                }
            }
        }
        double governorHeight = 0;
        Human governor = null;
        correctInput = false;
        exceptionMsg = null;
        while (!correctInput) {
            quietPrintln("Введите рост губернатора города (double), " +
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
                        exceptionMsg = "Недопустимое значение человеческого роста: " +
                                "рост должен быть больше 0.";
                } catch (NumberFormatException nfe) {
                    exceptionMsg = "Не удалось определить рост губернатора города." +
                            "Человеческий рост должен быть задан числом типа double.";
                }
                if (!correctInput) {
                    System.out.println(exceptionMsg);
                    if (scriptExecution) {
                        criticalException = new IllegalArgumentException(exceptionMsg);
                        return null;
                    }
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

    /**
     * Prints '\n' only if this.quiet != true
     */
    protected void quietPrintln() { quietPrintln(""); }

    /**
     * Prints the specified string + '\n' only if this.quiet != true
     * @param message - the string to print
     */
    protected void quietPrintln(String message) {
        if (quiet)
            return;
        System.out.println(message);
    }

    /**
     * Prints the specified string only if this.quiet != true
     * @param message - the string to print
     */
    protected void quietPrint(String message) {
        if (quiet)
            return;
        System.out.print(message);
    }

    /**
     * Reads the users answer for some question. And returns true if the answer was "д",
     * "да", "y" or "yes" and false false if the answer was "н", "нет", "n" or "no". If
     * user types something else asks user to input one of this answers again. This method
     * is not case sensitive.
     * @return true if the answer was "д", "да", "y" or "yes" and false false if the
     * answer was "н", "нет", "n" or "no"
     */
    protected boolean checkAnswer() {
        String answer;
        while (true) {
            System.out.println("Введите Д, если да;  Н, если нет:");
            answer = mainSc.nextLine().trim().split(" ")[0].toLowerCase();
            switch (answer) {
                case "д":
                case "да":
                case "y":
                case "yes":
                    return true;
                case "н":
                case "нет":
                case "n":
                case "no":
                    return false;
            }
        }
    }

    /**
     * Launches this CityCollectionManager and executes commands from the saved Scanner
     */
    public void work() {
        String[] command;
        City city;
        String key;
        int removed;
        while (sc.hasNext()) {
            command = sc.nextLine().split(" ");
            while (command.length < 1)
                command = sc.nextLine().split(" ");
            command[0] = command[0].toLowerCase();
            switch (command[0]) {
                case "help":
                    try (Scanner cmdHelpSc = new Scanner(new File("./Command help.txt"))) {
                        while (cmdHelpSc.hasNext()) {
                            System.out.println(cmdHelpSc.nextLine());
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("Не найден файл справки по доступным командам.");
                        System.out.println(e.getMessage());
                    }
                    break;
                case "info":
                    System.out.println("Collection info:\n\tType:\n\t\t" + cityHashtable.getClass().getSimpleName() +
                            "<" + String.class.getSimpleName() +
                            ", " + City.class.getSimpleName() +
                            ">\n\tInitialization time:\n\t\t" + initDate +
                            "\n\tSize:\n\t\t" + cityHashtable.size() + " element" + ((cityHashtable.size() == 1) ? "" : "s"));
                    break;
                case "show":
                    System.out.println(cityHashtable);
                    break;
                case "insert":
                    key = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
                    city = inputElement();
                    cityHashtable.putWithUniqId(key, city);
                    quietPrintln("В коллекцию по ключу " + key + " добавлен город " +
                            cityHashtable.get(key).getFullDescription());
                    break;
                case "update":
                    if (command.length > 1) {
                        try {
                            long id = Long.valueOf(command[1]);
                            key = cityHashtable.getKeyForId(id);
                            if (key == null) {
                                String exceptionMsg = "В коллекции нет города с id=" + id;
                                System.out.println(exceptionMsg);
                                criticalException = new NullPointerException(exceptionMsg);
                            }
                            else {
                                city = cityHashtable.get(key);
                                inputElement(city);
                                quietPrintln("Данные города " + city + " обновлены.");
                                quietPrintln("Ключ: " + key +
                                        "\nНовые данные города " +
                                        cityHashtable.get(key).getFullDescription());
                            }
                        } catch (NumberFormatException nfe) {
                            System.out.println("Не удалось определить id (long).");
                            System.out.println(nfe.getMessage());
                            criticalException = nfe;
                        }
                    } else {
                        System.out.println("Команда update принимает обязательный аргумент id (long).");
                        criticalException = new NullPointerException("Команда update принимает обязательный аргумент id (long).");
                    }
                    break;
                case "remove_key":
                    key = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
                    city = cityHashtable.remove(key);
                    if (city != null)
                        quietPrintln("Из коллекции по ключу " + key + " удален " +
                                city.getFullDescription());
                    else
                        quietPrintln("В коллекции нет соответствия для ключа " + key + ".");
                    break;
                case "clear":
                    cityHashtable.clear();
                    quietPrintln("Все города из коллекции удалены.");
                    break;
                case "save":
                    if (filename == null) {
                        System.out.println("Не указано имя файла для записи.");
                        System.out.println("Воспользуйтесь командой save_as с указанием имени файла.");
                        criticalException = new NullPointerException("Не указано имя файла для записи.");
                    } else
                        save();
                    break;
                case "save_as":
                    if (command.length > 1)
                        save(String.join(" ", Arrays.copyOfRange(command, 1, command.length)));
                    else {
                        System.out.println("Команда save_as принимает обязательный аргумент file_name (String).");
                        criticalException = new NullPointerException("Команда save_as принимает обязательный аргумент file_name (String).");
                    }
                    break;
                case "load":
                    if (command.length > 1) {
                        String loadFilename = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
                        load(loadFilename);
                        if (nonCriticalLoadExceptions > 0 && scriptExecution) {
                            System.out.println("Загрузка коллекции из файла " +
                                               loadFilename + " прошла с ошибками.");
                            System.out.println("Количество ошибок: " + nonCriticalLoadExceptions);
                            System.out.println("Продолжить выполнение скрипта?");
                            if (!checkAnswer()) {
                                quietScriptOut = true;
                                return;
                            }
                        }
                    }
                    else {
                        System.out.println("Команда load принимает обязательный аргумент file_name (String).");
                        criticalException = new NullPointerException("Команда load принимает обязательный аргумент file_name (String).");
                    }
                    break;
                case "execute_script":
                    if (command.length > 1) {
                        String exeFilename = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
                        File exeFile = new File(exeFilename);
                        if (!exeFile.isFile()) {
                            criticalException = new IOException(exeFilename +
                                    " is not a normal file. Execution is forbidden.");
                            System.out.println(criticalException.getMessage());
                        } else {
                            Scanner curScanner = sc;
                            boolean curScriptExecution = scriptExecution;
                            boolean curQuiet = quiet;
                            try (Scanner exeFileSc = new Scanner(exeFile)) {
                                sc = exeFileSc;
                                scriptExecution = quiet = true;
                                criticalException = null;
                                quietScriptOut = false;
                                work();
                                quiet = curQuiet;
                                scriptExecution = curScriptExecution;
                                sc = curScanner;
                                if (criticalException != null)
                                    System.out.println("Выполнение скрипта прервано.");
                                else if (quietScriptOut)
                                    quietPrintln("Выполнение скрипта прервано пользователем.");
                                else
                                    quietPrintln("Выполнение скрипта завершено.");
                            } catch (FileNotFoundException fnfe) {
                                System.out.print("Ошибка при открытии файла ");
                                System.out.println(fnfe.getMessage());
                                criticalException = fnfe;
                            }
                        }
                    } else {
                        System.out.println("Команда execute_script принимает обязательный аргумент file_name (String).");
                        criticalException = new NullPointerException("Команда execute_script принимает обязательный аргумент file_name (String).");
                    }
                    break;
                case "exit":
                    if (scriptExecution)
                        return;
                    System.exit(0);
                case "remove_greater":
                    city = inputElement();
                    removed = 0;
                    for (City collectionCity : cityHashtable.values()) {
                        if (city.compareTo(collectionCity) < 0) {
                            cityHashtable.removeValue(collectionCity);
                            removed++;
                        }
                    }
                    quietPrintln("Удалено городов: " + removed);
                    break;
                case "remove_lower":
                    city = inputElement();
                    removed = 0;
                    for (City collectionCity : cityHashtable.values()) {
                        if (city.compareTo(collectionCity) > 0) {
                            cityHashtable.removeValue(collectionCity);
                            removed++;
                        }
                    }
                    quietPrintln("Удалено городов: " + removed);
                    break;
                case "replace_if_greater":
                    key = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
                    city = inputElement();
                    City cityToBeReplaced = cityHashtable.get(key);
                    if (city.compareTo(cityToBeReplaced) > 0) {
                        cityHashtable.putWithUniqId(key, city);
                        quietPrintln("В коллекции заменен город по ключу " + key + ".");
                        quietPrintln("Новое значение: " +
                                     cityHashtable.get(key).getFullDescription());
                    }
                    break;
                case "sum_of_agglomeration":
                    int aggSum = 0;
                    Integer agglomeration;
                    for (City collectionCity : cityHashtable.values()) {
                        agglomeration = collectionCity.getAgglomeration();
                        if (agglomeration != null) aggSum += agglomeration;
                    }
                    System.out.println("Сумма значений agglomeration всех городов " +
                            "коллекции: " + aggSum);
                    break;
                case "max_by_standard_of_living":
                    if (cityHashtable.size() == 0)
                        System.out.println("В коллекции нет городов.");
                    else {
                        StandardOfLiving maxStandard = null;
                        StandardOfLiving standard;
                        city = null;
                        for (City collectionCity : cityHashtable.values()) {
                            standard = collectionCity.getStandardOfLiving();
                            if (maxStandard == null || maxStandard.compareTo(standard) < 0) {
                                city = collectionCity;
                                maxStandard = standard;
                            }
                        }
                        try {
                            System.out.println(city.getFullDescription());
                        } catch (NullPointerException ignore) {} //cityHashtable.size != 0
                    }
                    break;
                case "filter_less_than_climate":
                    if (command.length > 1) {
                        try {
                            Climate climate = Climate.valueOf(command[1].toUpperCase());
                            for (City collectionCity : cityHashtable.values()) {
                                if (collectionCity.getClimate().compareTo(climate) < 0)
                                    System.out.println(collectionCity.getFullDescription());
                            }
                        } catch (IllegalArgumentException iae) {
                            String msg = "Не удалось определить тип климата.\n" + iae.getMessage();
                            System.out.println(msg);
                            quietPrintln("Доступные варианты: " + ObjectsToStr(Climate.values()));
                            criticalException = new NullPointerException(msg);
                        }
                    } else {
                        System.out.println("Команда filter_less_than_climate принимает обязательный аргумент climate (String).");
                        criticalException = new NullPointerException("Команда filter_less_than_climate принимает обязательный аргумент climate (String).");
                    }
                    break;
            }
            if (criticalException != null && scriptExecution) {
                System.out.println("Ошибка при выполнении команды: " +
                                   String.join(" ", command));
                return;
            }
            if (quietScriptOut && scriptExecution) return;
        }
    }

    /**
     * The main method for starting the application
     * @param args - command line parameters
     */
    public static void main(String[] args) {
        CityCollectionManager ccm = new CityCollectionManager();
        if (args.length > 0) {
            String path = "";
            if (args[0].equals("-filename")) {
                if (args.length > 1)
                    path = args[1];
            } else {
                path = System.getenv(args[0]);
            }
            System.out.println(path);
            if (ccm.load(path) < 0)
                System.out.println("Воспользуйтесь командой load, чтобы загрузить коллекцию.");
        }
        ccm.work();
    }
}
