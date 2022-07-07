package pl.devcezz.shelter.catalogue

import io.vavr.control.Try
import pl.devcezz.shelter.commons.commands.Result
import pl.devcezz.shelter.commons.events.DomainEvent
import pl.devcezz.shelter.commons.events.DomainEvents
import spock.lang.Specification

import static pl.devcezz.shelter.catalogue.AnimalEvent.AnimalCreatedEvent

class SaveAnimalToCatalogueSpec extends Specification {

    DomainEvents publisher = Mock()
    CatalogueConfig config = new CatalogueConfig(new InMemoryCatalogueRepository(), publisher)

    Catalogue catalogue = config.catalogue()

    def 'should add new animal to catalogue'() {
        when:
            Try<Result> result = catalogue.addNewAnimal(
                    UUID.randomUUID(), "Azor", 5, "Dog", "Male")
        then:
            result.isSuccess()
            result.get() == Result.Success
        and:
            1 * publisher.publish(_ as AnimalCreatedEvent)
    }

    def 'should fail when adding animal if publisher fails'() {
        given:
            publisherDoesNotWork()
        when:
            Try<Result> result = catalogue.addNewAnimal(
                    UUID.randomUUID(), "Azor", 5, "Dog", "Male")
        then:
            result.isFailure()
    }

    def publisherDoesNotWork() {
        publisher.publish(_ as DomainEvent) >> { throw new IllegalArgumentException() }
    }


}
