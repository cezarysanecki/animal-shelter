package pl.csanecki.animalshelter.webservice.foo.adopt;

import pl.devcezz.cqrs.command.Command;

import java.util.UUID;

public class AdoptAnimalCommand implements Command {

    public final UUID id;

    AdoptAnimalCommand(final UUID id) {
        this.id = id;
    }
}
