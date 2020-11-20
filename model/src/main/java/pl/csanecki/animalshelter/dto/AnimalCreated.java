package pl.csanecki.animalshelter.dto;

public class AnimalCreated {

    public final int id;
    public final String name;
    public final String kind;
    public final int age;

    public AnimalCreated(int id, String name, String kind, int age) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.age = age;
    }
}
