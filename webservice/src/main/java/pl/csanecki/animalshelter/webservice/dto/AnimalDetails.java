package pl.csanecki.animalshelter.webservice.dto;

public class AnimalDetails {

    public final int id;
    public final String name;
    public final String kind;
    public final int age;
    public final String admittedAt;
    public final String adoptedAt;

    public AnimalDetails(int id, String name, String kind, int age, String admittedAt, String adoptedAt) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.age = age;
        this.admittedAt = admittedAt;
        this.adoptedAt = adoptedAt;
    }
}
