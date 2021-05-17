package pl.csanecki.animalshelter.webservice.web.addanimal;

import pl.devcezz.cqrs.command.Command;

import java.util.UUID;

public class AddAnimalCommand implements Command {

    private final UUID uuid = UUID.randomUUID();
    private final String name;
    private final String kind;
    private final int age;

    public AddAnimalCommand(final String name, final String kind, final int age) {
        this.name = name;
        this.kind = kind;
        this.age = age;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public int getAge() {
        return age;
    }
}
