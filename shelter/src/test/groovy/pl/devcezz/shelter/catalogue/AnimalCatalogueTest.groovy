package pl.devcezz.shelter.catalogue

import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

class AnimalCatalogueTest extends Specification {

    AnimalRepository repository = new InMemoryCatalogueRepository()
    ApplicationEventPublisher publisher = Mock()

    def facade = new CatalogueConfig()
            .animalFacade(repository, publisher)

    def 'should update animal data'() {
        given:
            def animalUuid = anyAnimalUuid()

        when:
            facade.save(animalUuid, "Azor", 11, "Dog", "male")
        and:
            def animal = getAnimal(animalUuid)

        then: 'check if fields are mapped properly'
            animal.getName() == "Azor"
            animal.getAge() == 11
            animal.getSpecies() == "Dog"
            animal.getGender().name() == "MALE"

        when:
            facade.update(animalUuid, "Tweety", 2, "Canary", "female")
        and:
            animal = getAnimal(animalUuid)

        then: 'check if fields are mapped properly'
            animal.getName() == "Tweety"
            animal.getAge() == 2
            animal.getSpecies() == "Canary"
            animal.getGender().name() == "FEMALE"
    }

    private Animal getAnimal(UUID animalUuid) {
        repository.findByAnimalId(AnimalId.of(animalUuid))
                .orElseThrow(IllegalStateException::new)
    }

    private UUID anyAnimalUuid() {
        UUID.randomUUID()
    }

}