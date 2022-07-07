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
        given:
            AnimalId animalId = anyAnimalId()
        when:
            catalogue.addAnimal(
                    animalId, Name.of("Azor"), Age.of(5), Species.of("dog"), Gender.Male)
        then:
            findAnimal(animalId).isDefined()
        when:
            catalogue.updateAnimal(
                    animalId, Name.of("Sonia"), Age.of(4), Species.of("dog"), Gender.Female)
        then:
            findAnimal(animalId).isDefined()
        when:
            catalogue.deleteAnimal(animalId)
        then:
            findAnimal(animalId).isEmpty()
    }

    def 'should not allow to modify confirmed animal'() {
        given:
            AnimalId animalId = anyAnimalId()
        and:
            catalogue.addAnimal(
                    animalId, Name.of("Azor"), Age.of(5), Species.of("dog"), Gender.Male)
        when:
            catalogue.confirmAnimal(animalId)
        and:
            Try<Result> updateResult = catalogue.updateAnimal(
                    animalId, Name.of("Sonia"), Age.of(4), Species.of("dog"), Gender.Female)
        then:
            updateResult.isFailure()
        when:
            Try<Result> deleteResult = catalogue.deleteAnimal(animalId)
        then:
            deleteResult.isFailure()
    }

    Option<Animal> findAnimal(AnimalId animalId) {
        return repository.findBy(animalId)
    }
}
