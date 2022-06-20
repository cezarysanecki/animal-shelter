package pl.devcezz.shelter.catalogue;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface AnimalRepository extends CrudRepository<Animal, Long> {

    Optional<Animal> findByAnimalId(AnimalId animalId);
}
