package pl.devcezz.shelter.catalogue;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

interface AnimalRepository extends CrudRepository<Animal, UUID> {

    Optional<Animal> findByAnimalId(AnimalId animalId);

    @Query("UPDATE Animal a SET a.status = 'DELETED' WHERE a.animalId = ?1")
    @Modifying
    void deleteAnimalDataFor(AnimalId animalId);
}
