package pl.devcezz.shelter.catalogue;

import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.catalogue.exception.AnimalNotFound;

import javax.transaction.Transactional;

@RequiredArgsConstructor
class AnimalService {

    private final AnimalRepository animalRepository;

    @Transactional
    void save(Animal animal) {
        animalRepository.save(animal);
    }

    @Transactional
    void update(Animal animal) {
        Animal foundAnimal = animalRepository.findByAnimalId(animal.getAnimalId())
                .orElseThrow(() -> new AnimalNotFound(animal.getAnimalId().getValue()));

        animalRepository.save(Animal.of(
                foundAnimal.getId(),
                animal.getAnimalId(),
                animal.getName(),
                animal.getAge(),
                animal.getSpecies(),
                animal.getGender()
        ));
    }

    @Transactional
    void delete(AnimalId animalId) {
        Animal foundAnimal = animalRepository.findByAnimalId(animalId)
                .orElseThrow(() -> new AnimalNotFound(animalId.getValue()));
        animalRepository.delete(foundAnimal);
    }
}
