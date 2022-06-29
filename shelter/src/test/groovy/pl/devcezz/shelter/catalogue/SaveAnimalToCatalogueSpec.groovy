package pl.devcezz.shelter.catalogue

import io.vavr.control.Try
import pl.devcezz.shelter.commons.commands.Result
import pl.devcezz.shelter.commons.events.DomainEvents
import spock.lang.Specification

import static pl.devcezz.shelter.catalogue.AnimalEvent.*

class SaveAnimalToCatalogueSpec extends Specification {

    CatalogueDatabase catalogueDatabase = Mock()
    DomainEvents domainEvents = Mock()
    Catalogue catalogue = new Catalogue(catalogueDatabase, domainEvents)

    def 'should add a new animal to catalogue'() {
        given:
            databaseWorks()
        when:
            Try<Result> result = catalogue.addNewAnimal(UUID.randomUUID(), "Azor", 5, "Dog", "Male")
        then:
            result.isSuccess()
            result.get() == Result.Success
        and:
            1 * domainEvents.publish(_ as AnimalCreatedEvent)
    }

    def 'should fail when adding an animal if database fails'() {
        given:
            databaseDoesNotWork()
        when:
            Try<Result> result = catalogue.addNewAnimal(UUID.randomUUID(), "Azor", 5, "Dog", "Male")
        then:
            result.isFailure()
        and:
            0 * domainEvents.publish(_ as AnimalCreatedEvent)
    }

    void databaseWorks() {
        catalogueDatabase.saveNew(_ as Animal) >> null
    }

    void databaseDoesNotWork() {
        catalogueDatabase.saveNew(_ as Animal) >> { throw new IllegalStateException() }
    }


}
