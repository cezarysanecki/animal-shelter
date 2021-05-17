package pl.csanecki.animalshelter.webservice.web.adoptanimal;

import pl.devcezz.cqrs.command.Command;

import java.util.UUID;

public class AdoptAnimalCommand implements Command {

    private final UUID uuid;

    public AdoptAnimalCommand(final String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    public UUID getUuid() {
        return uuid;
    }
}
