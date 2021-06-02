package pl.csanecki.animalshelter.webservice.write.add;

import pl.devcezz.cqrs.command.Command;

import java.util.UUID;

public class AddAnimalCommand implements Command {

    public final UUID animalId;
    public final String name;
    public final String kind;
    public final int age;

    public AddAnimalCommand(final UUID animalId, final String name, final String kind, final int age) {
        this.animalId = animalId;
        this.name = name;
        this.kind = kind;
        this.age = age;
    }
}
