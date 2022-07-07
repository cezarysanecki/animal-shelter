package pl.devcezz.shelter.catalogue

import io.vavr.control.Try
import pl.devcezz.shelter.commons.commands.Result
import pl.devcezz.shelter.commons.events.DomainEvent
import pl.devcezz.shelter.commons.events.DomainEvents
import spock.lang.Specification

import static pl.devcezz.shelter.catalogue.AnimalEvent.AnimalDeletedEvent

class RemoveAnimalFromCatalogueSpec extends Specification {

    DomainEvents publisher = Mock()
    CatalogueConfig config = new CatalogueConfig(new InMemoryCatalogueRepository(), publisher)

    Catalogue catalogue = config.catalogue()

    def 'should remove existing animal from catalogue'() {
        given:
            AnimalId animalId = AnimalId.of(UUID.randomUUID())
        and:
            catalogue.addNewAnimal(
                    animalId.getValue(), "Azor", 5, "Dog", "Male")
        when:
            Try<Result> result = catalogue.removeExistingAnimal(animalId.getValue())
        then:
            result.isSuccess()
            result.get() == Result.Success
        and:
            1 * publisher.publish(_ as AnimalDeletedEvent)
    }

    def 'should reject removing not existing animal in catalogue'() {
        given:
            AnimalId animalId = AnimalId.of(UUID.randomUUID())
        when:
            Try<Result> result = catalogue.removeExistingAnimal(animalId.getValue())
        then:
            result.isSuccess()
            result.get() == Result.Rejection
        and:
            0 * publisher.publish(_)
    }

    def 'should fail when removing animal if publisher fails'() {
        given:
            publisherDoesNotWork()
        and:
            AnimalId animalId = AnimalId.of(UUID.randomUUID())
        and:
            catalogue.addNewAnimal(
                    animalId.getValue(), "Azor", 5, "Dog", "Male")
        when:
            Try<Result> result = catalogue.removeExistingAnimal(animalId.getValue())
        then:
            result.isFailure()
    }

    def publisherDoesNotWork() {
        publisher.publish(_ as DomainEvent) >> { throw new IllegalArgumentException() }
    }

}
