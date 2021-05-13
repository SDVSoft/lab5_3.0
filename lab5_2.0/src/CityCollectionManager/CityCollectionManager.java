package CityCollectionManager;

import Entities.City.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Hashtable;
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
    private Hashtable<String, City> cityHashtable;
    private Hashtable<Long, String> idHashtable;
    private java.util.Date initDate;
    private long nextId;

    CityCollectionManager() {
        this.cityHashtable = new Hashtable<>();
        this.idHashtable = new Hashtable<>();
        this.initDate = new java.util.Date();
        this.nextId = 1;
    }

    private void updateNextId() {
        while (idHashtable.containsKey(nextId)) nextId++;
    }

    public boolean add(String key, City city) {
        if (idHashtable.containsKey(city.getId()))
            return false;
        if (cityHashtable.containsKey(key))
            return false;
        idHashtable.put(city.getId(), key);
        cityHashtable.put(key, city);
        if (city.getId() == nextId) updateNextId();
        return true;
    }

    public boolean addWithNewId(String key, City city) {
        if (cityHashtable.containsKey(key))
            return false;
        //TODO: continue
        return true;
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
                    System.out.println("Collection info:\n\tType:\n\t\t" + cityHashtable.getClass().getSimpleName() +
                            "<" + String.class.getSimpleName() +
                            ", " + City.class.getSimpleName() +
                            ">\n\tInitialization time:\n\t\t" + initDate +
                            "\n\tSize:\n\t\t" + cityHashtable.size() + " element" + ((cityHashtable.size() == 1)? "" : "s"));
                    break;
                case "show":
                    System.out.println(cityHashtable);
                    break;
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

    public static void main(String[] args){
        CityCollectionManager ccm = new CityCollectionManager();
        ccm.work();
    }
}
