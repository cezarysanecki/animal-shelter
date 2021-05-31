package pl.csanecki.animalshelter.webservice.foo.adopt;

import pl.csanecki.animalshelter.webservice.foo.ShelterWriteRepository;
import pl.devcezz.cqrs.command.CommandHandler;

public class AdoptAnimalCommandHandler implements CommandHandler<AdoptAnimalCommand> {

    private final ShelterWriteRepository repository;

    AdoptAnimalCommandHandler(final ShelterWriteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(final AdoptAnimalCommand command) {

    }
}
