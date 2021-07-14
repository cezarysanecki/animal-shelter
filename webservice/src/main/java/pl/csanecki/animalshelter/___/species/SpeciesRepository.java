package pl.csanecki.animalshelter.___.species;

import io.vavr.collection.Set;

public interface SpeciesRepository {

    Set<Species> findAllSpecies();

    void save(Species species);
}
