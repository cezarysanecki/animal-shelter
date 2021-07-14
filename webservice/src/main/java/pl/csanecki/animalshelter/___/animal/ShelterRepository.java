package pl.csanecki.animalshelter.___.animal;

public interface ShelterRepository {

    void save(Animal animal);

    AnimalsInShelter queryForAnimalsInShelter();

    Shelter queryForShelter();
}
