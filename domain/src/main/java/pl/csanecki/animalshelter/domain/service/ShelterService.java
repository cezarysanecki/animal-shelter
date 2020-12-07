package pl.csanecki.animalshelter.domain.service;

public class ShelterService {

    private final AnimalRepository animalRepository;

    public ShelterService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }
}
