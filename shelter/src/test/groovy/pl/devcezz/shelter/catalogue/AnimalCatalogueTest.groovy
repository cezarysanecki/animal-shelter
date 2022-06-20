package pl.devcezz.shelter.catalogue

import org.springframework.context.ApplicationEventPublisher
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent
import spock.lang.Specification

class AnimalCatalogueTest extends Specification {

    AnimalRepository repository = new InMemoryCatalogueRepository()
    ApplicationEventPublisher publisher = Mock()

    def facade = new CatalogueConfig()
            .animalFacade(repository, publisher)

    def 'should add animal to catalogue'() {
        given:
            def animalUuid = anyAnimalUuid()

        when:
            facade.save(animalUuid, "Azor", 11, "Dog", "male")
        and:
            def animal = getAnimal(animalUuid)

        then:
            animal.getName() == "Azor"
            animal.getAge() == 11
            animal.getSpecies() == "Dog"
            animal.getGender().name() == "MALE"
        and:
            1 * publisher.publishEvent(_ as AnimalCreatedEvent)
    }

    def 'should update animal in catalogue'() {
        given:
            def animalUuid = anyAnimalUuid()
        and:
            facade.save(animalUuid, "Azor", 11, "Dog", "male")

        when:
            facade.update(animalUuid, "Tweety", 2, "Canary", "female")
        and:
            def animal = getAnimal(animalUuid)

        then:
            animal.getName() == "Tweety"
            animal.getAge() == 2
            animal.getSpecies() == "Canary"
            animal.getGender().name() == "FEMALE"
        and:
            0 * publisher.publishEvent(_)
    }

    private Animal getAnimal(UUID animalUuid) {
        repository.findByAnimalId(AnimalId.of(animalUuid))
                .orElseThrow(IllegalStateException::new)
    }

    private UUID anyAnimalUuid() {
        UUID.randomUUID()
    }

}