package pl.devcezz.animalshelter.commons.mail.model;

public class AdoptionMail {

    private final String name;
    private final String species;

    public AdoptionMail(final String name, final String species) {
        this.name = name;
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }
}
