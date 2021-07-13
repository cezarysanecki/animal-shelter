package pl.csanecki.animalshelter.___;

import pl.csanecki.animalshelter.___.species.Species;

interface ShelterRepository {

    boolean contains(Species species);

    void save(Species species);

    void save(Animal animal);

    AnimalsInShelter queryForAnimalsInShelter();

    Shelter queryForShelter();
}
