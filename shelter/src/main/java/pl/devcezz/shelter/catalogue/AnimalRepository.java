package pl.devcezz.shelter.catalogue;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

interface AnimalRepository extends CrudRepository<Animal, UUID> {

    Optional<Animal> findByAnimalId(AnimalId animalId);
}
