package pl.csanecki.animalshelter.___.species;

import io.vavr.collection.Set;
import pl.devcezz.cqrs.command.CommandHandler;

class AddingSpecies implements CommandHandler<AddSpeciesCommand> {

    private final SpeciesRepository speciesRepository;

    AddingSpecies(final SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public void handle(final AddSpeciesCommand command) {
        Species species = new Species(command.getSpecies());

        if (isDuplicated(species)) {
            throw new IllegalArgumentException("Species is already acceptable: " + species.getValue());
        }

        speciesRepository.save(species);
    }

    private boolean isDuplicated(Species species) {
        Set<Species> shelterSpecies = speciesRepository.findAllSpecies();
        return shelterSpecies.contains(species);
    }
}
