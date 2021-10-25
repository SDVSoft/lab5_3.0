package entities.woker;

import entities.common.attributes.Coordinates;
import entities.organization.Organization;
import entities.woker.attributes.Position;
import entities.woker.attributes.Status;

public class Worker {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int salary; //Значение поля должно быть больше 0
    private java.time.LocalDate startDate; //Поле не может быть null
    private Position position; //Поле может быть null
    private Status status; //Поле может быть null
    private Organization organization; //Поле не может быть null

    //TODO
}
