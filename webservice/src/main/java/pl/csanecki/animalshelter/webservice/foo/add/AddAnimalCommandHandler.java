package pl.csanecki.animalshelter.webservice.foo.add;

import pl.csanecki.animalshelter.webservice.foo.ShelterWriteRepository;
import pl.devcezz.cqrs.command.CommandHandler;

public class AddAnimalCommandHandler implements CommandHandler<AddAnimalCommand> {

    private final ShelterWriteRepository repository;

    AddAnimalCommandHandler(final ShelterWriteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(final AddAnimalCommand command) {

    }
}
