package pl.devcezz.shelter.catalogue

import io.vavr.control.Option
import io.vavr.control.Try
import pl.devcezz.shelter.commons.commands.Result
import pl.devcezz.shelter.commons.events.DomainEvents
import spock.lang.Specification

import static pl.devcezz.shelter.catalogue.AnimalEvent.*

class RemoveAnimalFromCatalogueSpec extends Specification {

    CatalogueDatabase catalogueDatabase = Mock()
    DomainEvents domainEvents = Mock()
    Catalogue catalogue = new Catalogue(catalogueDatabase, domainEvents)

    AnimalId animalId = AnimalId.of(UUID.randomUUID())

    def 'should remove a new animal to catalogue'() {
        given:
            databaseWorks()
        when:
            Try<Result> result = catalogue.removeExistingAnimal(animalId.getValue())
        then:
            result.isSuccess()
            result.get() == Result.Success
        and:
            1 * domainEvents.publish(_ as AnimalDeletedEvent)
    }

    def 'should fail when removing an animal if database fails'() {
        given:
            databaseDoesNotWork()
        when:
            Try<Result> result = catalogue.removeExistingAnimal(animalId.getValue())
        then:
            result.isFailure()
        and:
            0 * domainEvents.publish(_ as AnimalCreatedEvent)
    }

    void databaseWorks() {
        catalogueDatabase.findBy(animalId) >> Option.of(
                Animal.ofNew(animalId.getValue(), "Azor", 5, "Dog", "Male"))
        catalogueDatabase.updateStatus(_ as Animal) >> null
    }

    void databaseDoesNotWork() {
        catalogueDatabase.findBy(animalId) >> Option.of(
                Animal.ofNew(animalId.getValue(), "Azor", 5, "Dog", "Male"))
        catalogueDatabase.updateStatus(_ as Animal) >> { throw new IllegalStateException() }
    }


}
