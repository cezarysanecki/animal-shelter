package pl.devcezz.shelter.catalogue

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = [CatalogueConfig.class, CatalogueDbConfig.class])
@EnableAutoConfiguration
class CatalogueTimestampsIT extends Specification {

    @Autowired
    AnimalFacade animalFacade

    @Autowired
    AnimalRepository animalRepository

    def 'test'() {
        given:
            def animalUuid = anyAnimalUuid()

        when:
            saveAnimalData(animalUuid, dog("Azor"))

        and:
            updateAnimalData(animalUuid, dog("Tobiasz"))

        then: 'modification timestamp is greater than creation timestamp'
            def animal = getAnimal(animalUuid)
            animal.creationTimestamp < animal.modificationTimestamp
    }

    void saveAnimalData(UUID animalUuid, AnimalData data) {
        animalFacade.save(animalUuid, data.getName(), data.getAge(), data.getSpecies(), data.getGender())
    }

    void updateAnimalData(UUID animalUuid, AnimalData data) {
        animalFacade.update(animalUuid, data.getName(), data.getAge(), data.getSpecies(), data.getGender())
    }

    Animal getAnimal(UUID animalUuid) {
        animalRepository.findByAnimalId(AnimalId.of(animalUuid))
                .orElseThrow(IllegalArgumentException::new)
    }

    UUID anyAnimalUuid() {
        UUID.randomUUID()
    }

    AnimalData dog(String name = "Azor") {
        new AnimalData(name, 12, "Dog", "Male")
    }
}

class AnimalData {

    final String name
    final Integer age
    final String species
    final String gender

    AnimalData(String name, Integer age, String species, String gender) {
        this.name = name
        this.age = age
        this.species = species
        this.gender = gender
    }

}
