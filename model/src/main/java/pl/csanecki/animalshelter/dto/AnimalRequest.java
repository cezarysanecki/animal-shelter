package pl.csanecki.animalshelter.dto;

public class AnimalDto {

    public final String name;
    public final String kind;
    public final int age;

    public AnimalDto(String name, String kind, int age) {
        this.name = name;
        this.kind = kind;
        this.age = age;
    }
}
