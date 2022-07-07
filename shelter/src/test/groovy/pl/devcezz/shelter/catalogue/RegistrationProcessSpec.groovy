package pl.devcezz.shelter.catalogue

import io.vavr.control.Option
import io.vavr.control.Try
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.commons.commands.Result
import pl.devcezz.shelter.commons.events.DomainEvents
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted.proposalAcceptedNow
import static pl.devcezz.shelter.catalogue.Status.Deleted
import static pl.devcezz.shelter.catalogue.Status.Registered

class RegistrationProcessSpec extends Specification {

    DomainEvents publisher = Mock()
    CatalogueRepository repository = new InMemoryCatalogueRepository()
    CatalogueConfig config = new CatalogueConfig(repository, publisher)

    Catalogue catalogue = config.catalogue()
    CatalogueEventHandler handler = config.catalogueEventHandler()

    def 'should not allow to update animal when is already accepted'() {
        given:
            ProposalId proposalId = anyProposalId()
        and:
            AnimalId animalId = AnimalId.of(proposalId.getValue())
        and:
            catalogue.addNewAnimal(
                    animalId.getValue(), "Azor", 4, "Dog", "Male")
        when:
            handler.handle(proposalAcceptedNow(proposalId))
        and:
            Try<Result> result = catalogue.updateExistingAnimal(
                    animalId.getValue(), "Sonia", 3, "Cat", "Female")
        then:
            result.isFailure()
        and:
            animalIsPersistedAs(Registered, animalId)
    }

    def 'should not allow to remove animal when is already accepted'() {
        given:
            ProposalId proposalId = anyProposalId()
        and:
            AnimalId animalId = AnimalId.of(proposalId.getValue())
        and:
            catalogue.addNewAnimal(
                    animalId.getValue(), "Azor", 4, "Dog", "Male")
        when:
            handler.handle(proposalAcceptedNow(proposalId))
        and:
            Try<Result> result = catalogue.removeExistingAnimal(animalId.getValue())
        then:
            result.isFailure()
        and:
            animalIsPersistedAs(Registered, animalId)
    }

    def 'should accept animal even if was deleted'() {
        given:
            ProposalId proposalId = anyProposalId()
        and:
            AnimalId animalId = AnimalId.of(proposalId.getValue())
        when:
            catalogue.addNewAnimal(
                    animalId.getValue(), "Azor", 4, "Dog", "Male")
        and:
            catalogue.removeExistingAnimal(animalId.getValue())
        then:
            animalIsPersistedAs(Deleted, animalId)
        when:
            handler.handle(proposalAcceptedNow(proposalId))
        and:
            Try<Result> result = catalogue.removeExistingAnimal(animalId.getValue())
        then:
            result.isFailure()
        and:
            animalIsPersistedAs(Registered, animalId)
    }

    void animalIsPersistedAs(Status status, AnimalId animalId) {
        Animal animal = loadPersistedAnimal(animalId)
        assert animal.getStatus() == status
    }

    Animal loadPersistedAnimal(AnimalId animalId) {
        Option<Animal> loaded = repository.findBy(animalId)
        Animal animal = loaded.getOrElseThrow({
            new IllegalStateException("should have been persisted")
        })
        return animal
    }
}
