package pl.csanecki.animalshelter.___.species;

public interface SpeciesRepository {

    boolean contains(Species species);

    void save(Species species);
}
