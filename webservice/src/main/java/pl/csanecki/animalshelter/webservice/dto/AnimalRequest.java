package pl.csanecki.animalshelter.webservice.dto;

public class AnimalRequest {

    public final String name;
    public final String kind;
    public final int age;

    public AnimalRequest(String name, String kind, int age) {
        this.name = name;
        this.kind = kind;
        this.age = age;
    }
}
