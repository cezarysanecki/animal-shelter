package pl.devcezz.shelter.catalogue

import io.vavr.control.Option
import io.vavr.control.Try
import pl.devcezz.shelter.commons.commands.Result
import pl.devcezz.shelter.commons.events.DomainEvents
import pl.devcezz.shelter.commons.model.Age
import pl.devcezz.shelter.commons.model.Gender
import pl.devcezz.shelter.commons.model.Name
import pl.devcezz.shelter.commons.model.Species
import spock.lang.Specification

import static pl.devcezz.shelter.catalogue.AnimalFixture.anyAnimalId

class RegistrationProcessSpec extends Specification {

    DomainEvents publisher = Mock()
    CatalogueRepository repository = new InMemoryCatalogueRepository()
    CatalogueConfig config = new CatalogueConfig(repository, publisher)

    Catalogue catalogue = config.catalogue()

    def 'should properly do operation on animal'() {
        given: "Prepare any animal id."
            AnimalId animalId = anyAnimalId()
        when: "Add animal to catalogue."
            catalogue.addAnimal(
                    animalId, Name.of("Azor"), Age.of(5), Species.of("dog"), Gender.Male)
        then: "Check if animal is in system."
            findAnimal(animalId).isDefined()
        when: "Update animal."
            catalogue.updateAnimal(
                    animalId, Name.of("Sonia"), Age.of(4), Species.of("dog"), Gender.Female)
        then: "Check if animal is still in system."
            findAnimal(animalId).isDefined()
        when: "Delete animal."
            catalogue.deleteAnimal(animalId)
        then: "Check if animal is not in system."
            findAnimal(animalId).isEmpty()
    }

    def 'should not allow to modify confirmed animal'() {
        given: "Prepare any animal id."
            AnimalId animalId = anyAnimalId()
        and: "Add animal to catalogue."
            catalogue.addAnimal(
                    animalId, Name.of("Azor"), Age.of(5), Species.of("dog"), Gender.Male)
        when: "Confirm animal."
            catalogue.confirmAnimal(animalId)
        and: "Try to update animal."
            Try<Result> updateResult = catalogue.updateAnimal(
                    animalId, Name.of("Sonia"), Age.of(4), Species.of("dog"), Gender.Female)
        then: "Operation is not allowed."
            updateResult.isFailure()
        when: "Try to delete animal."
            Try<Result> deleteResult = catalogue.deleteAnimal(animalId)
        then: "Operation is not allowed."
            deleteResult.isFailure()
    }

    def 'should not allow to confirm confirmed animal'() {
        given: "Prepare any animal id."
            AnimalId animalId = anyAnimalId()
        and: "Add animal to catalogue."
            catalogue.addAnimal(
                    animalId, Name.of("Azor"), Age.of(5), Species.of("dog"), Gender.Male)
        when: "Confirm animal."
            catalogue.confirmAnimal(animalId)
        and: "Confirm animal once again."
            Try<Result> result = catalogue.confirmAnimal(animalId)
        then: "Operation is not allowed."
            result.isFailure()
    }

    Option<Animal> findAnimal(AnimalId animalId) {
        return repository.findBy(animalId)
    }
}
