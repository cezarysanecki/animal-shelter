package pl.csanecki.animalshelter.webservice.write.adopt;

import pl.devcezz.cqrs.command.Command;

import java.util.UUID;

public class AdoptAnimalCommand implements Command {

    public final UUID animalId;

    public AdoptAnimalCommand(final UUID animalId) {
        this.animalId = animalId;
    }
}
