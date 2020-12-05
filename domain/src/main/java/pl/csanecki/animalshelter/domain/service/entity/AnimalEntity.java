package pl.csanecki.animalshelter.domain.service.entity;

public class AnimalEntity {

    public final int id;
    public final String name;
    public final String kind;
    public final int age;
    public final String admittedAt;
    public final String adoptedAt;

    public AnimalEntity(String name, String kind, int age) {
        this.id = -1;
        this.name = name;
        this.kind = kind;
        this.age = age;
        this.admittedAt = null;
        this.adoptedAt = null;
    }

    public AnimalEntity(int id, String name, String kind, int age, String admittedAt, String adoptedAt) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.age = age;
        this.admittedAt = admittedAt;
        this.adoptedAt = adoptedAt;
    }
}
