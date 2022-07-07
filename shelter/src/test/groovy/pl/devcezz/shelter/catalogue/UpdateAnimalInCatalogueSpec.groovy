package pl.devcezz.shelter.catalogue

import io.vavr.control.Try
import pl.devcezz.shelter.commons.commands.Result
import pl.devcezz.shelter.commons.events.DomainEvents
import spock.lang.Specification

class UpdateAnimalInCatalogueSpec extends Specification {

    DomainEvents publisher = Mock()
    CatalogueConfig config = new CatalogueConfig(new InMemoryCatalogueRepository(), publisher)

    Catalogue catalogue = config.catalogue()

    def 'should not publish event when updating animal'() {
        given:
            AnimalId animalId = AnimalId.of(UUID.randomUUID())
        and:
            catalogue.addNewAnimal(
                    animalId.getValue(), "Azor", 5, "Dog", "Male")
        when:
            Try<Result> result = catalogue.updateExistingAnimal(
                    animalId.getValue(), "Ciapek", 3, "Dog", "Male")
        then:
            result.isSuccess()
            result.get() == Result.Success
        and:
            0 * publisher.publish(_)
    }

    def 'should fail when updating removed animal'() {
        given:
            AnimalId animalId = AnimalId.of(UUID.randomUUID())
        and:
            catalogue.addNewAnimal(
                    animalId.getValue(), "Azor", 5, "Dog", "Male")
        and:
            catalogue.removeExistingAnimal(animalId.getValue())
        when:
            Try<Result> result = catalogue.updateExistingAnimal(
                    animalId.getValue(), "Ciapek", 3, "Dog", "Male")
        then:
            result.isFailure()
    }

    def 'should reject updating not existing animal in catalogue'() {
        given:
            AnimalId animalId = AnimalId.of(UUID.randomUUID())
        when:
            Try<Result> result = catalogue.updateExistingAnimal(
                    animalId.getValue(), "Ciapek", 3, "Dog", "Male")
        then:
            result.isSuccess()
            result.get() == Result.Rejection
        and:
            0 * publisher.publish(_)
    }

}
