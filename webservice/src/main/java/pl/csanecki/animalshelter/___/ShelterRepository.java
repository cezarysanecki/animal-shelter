package pl.csanecki.animalshelter.___;

import io.vavr.collection.List;

interface ShelterRepository {

    List<Species> findAllSpecies();

    void save(Species species);
}
