package pl.csanecki.animalshelter.webservice.dto;

public class AdmittedAnimal {

    public final String name;
    public final String kind;
    public final int age;

    public AdmittedAnimal(String name, String kind, int age) {
        this.name = name;
        this.kind = kind;
        this.age = age;
    }
}
