package pl.csanecki.animalshelter.___;

import pl.csanecki.animalshelter.___.species.AddSpeciesCommand;
import pl.csanecki.animalshelter.___.species.Species;
import pl.devcezz.cqrs.command.CommandHandler;

class AddingSpecies implements CommandHandler<AddSpeciesCommand> {

    private final ShelterRepository shelterRepository;

    AddingSpecies(final ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Override
    public void handle(final AddSpeciesCommand command) {
        Species species = new Species(command.getSpecies());

        if (shelterRepository.contains(species)) {
            throw new IllegalArgumentException("Species " + species.getValue() + " already exists");
        }

        shelterRepository.save(species);
    }
}
