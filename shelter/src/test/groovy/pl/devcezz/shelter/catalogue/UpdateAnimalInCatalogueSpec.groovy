package pl.devcezz.shelter.catalogue

import io.vavr.control.Option
import io.vavr.control.Try
import pl.devcezz.shelter.commons.commands.Result
import pl.devcezz.shelter.commons.events.DomainEvents
import spock.lang.Specification

class UpdateAnimalInCatalogueSpec extends Specification {

    CatalogueDatabase catalogueDatabase = Mock()
    DomainEvents domainEvents = Mock()
    Catalogue catalogue = new Catalogue(catalogueDatabase, domainEvents)

    AnimalId animalId = AnimalId.of(UUID.randomUUID())

    def 'should not publish event when updating animal'() {
        given:
            databaseWorks()
        when:
            Try<Result> result = catalogue.updateExistingAnimal(animalId.getValue(), "Ciapek", 3, "Dog", "Male")
        then:
            result.isSuccess()
            result.get() == Result.Success
        and:
            0 * domainEvents.publish(_)
    }

    def 'should fail when updating animal if database fails'() {
        given:
            databaseDoesNotWork()
        when:
            Try<Result> result = catalogue.updateExistingAnimal(animalId.getValue(), "Ciapek", 3, "Dog", "Male")
        then:
            result.isFailure()
    }

    void databaseWorks() {
        catalogueDatabase.findByAnimalId(animalId) >> Option.of(
                Animal.ofNew(animalId.getValue(), "Azor", 5, "Dog", "Male"))
        catalogueDatabase.update(_ as Animal) >> null
    }

    void databaseDoesNotWork() {
        catalogueDatabase.findByAnimalId(animalId) >> Option.of(
                Animal.ofNew(animalId.getValue(), "Azor", 5, "Dog", "Male"))
        catalogueDatabase.update(_ as Animal) >> { throw new IllegalStateException() }
    }


}
