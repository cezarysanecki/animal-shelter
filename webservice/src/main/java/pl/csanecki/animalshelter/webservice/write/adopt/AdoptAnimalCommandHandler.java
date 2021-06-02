package pl.csanecki.animalshelter.webservice.write.adopt;

import pl.csanecki.animalshelter.webservice.write.ShelterWriteRepository;
import pl.devcezz.cqrs.command.CommandHandler;

public class AdoptAnimalCommandHandler implements CommandHandler<AdoptAnimalCommand> {

    private final ShelterWriteRepository repository;

    public AdoptAnimalCommandHandler(final ShelterWriteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(final AdoptAnimalCommand command) {
        repository.updateAdoptedAtToNow(command.animalId);
    }
}
