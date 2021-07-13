package pl.csanecki.animalshelter.___.species;

import pl.devcezz.cqrs.command.Command;

public class AddSpeciesCommand implements Command {

    private final String species;

    public AddSpeciesCommand(final String species) {
        this.species = species;
    }

    String getSpecies() {
        return species;
    }
}
