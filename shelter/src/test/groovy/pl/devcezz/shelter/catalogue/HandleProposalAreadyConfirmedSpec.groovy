package pl.devcezz.shelter.catalogue

import io.vavr.control.Try
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.commons.commands.Result
import pl.devcezz.shelter.commons.events.DomainEvents
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAlreadyConfirmed
import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAlreadyConfirmed.proposalAlreadyConfirmedNow
import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId

class HandleProposalAreadyConfirmedSpec extends Specification {

    DomainEvents publisher = Mock()
    CatalogueConfig config = new CatalogueConfig(new InMemoryCatalogueRepository(), publisher)

    Catalogue catalogue = config.catalogue()
    CatalogueEventHandler handler = config.catalogueEventHandler()

    def 'should not allow to update animal when is already confirmed'() {
        given:
            ProposalId proposalId = anyProposalId()
        and:
            AnimalId animalId = AnimalId.of(proposalId.getValue())
        and:
            catalogue.addNewAnimal(
                    animalId.getValue(), "Azor", 4, "Dog", "Male")
        and:
            catalogue.removeExistingAnimal(animalId.getValue())
        and:
            ProposalAlreadyConfirmed event = proposalAlreadyConfirmedNow(proposalId)
        when:
            handler.handle(event)
        and:
            Try<Result> result = catalogue.updateExistingAnimal(
                    animalId.getValue(), "Sonia", 3, "Cat", "Female")
        then:
            result.isFailure()
    }

    def 'should not allow to remove animal when is already confirmed'() {
        given:
            ProposalId proposalId = anyProposalId()
        and:
            AnimalId animalId = AnimalId.of(proposalId.getValue())
        and:
            catalogue.addNewAnimal(
                    animalId.getValue(), "Azor", 4, "Dog", "Male")
        and:
            catalogue.removeExistingAnimal(animalId.getValue())
        and:
            ProposalAlreadyConfirmed event = proposalAlreadyConfirmedNow(proposalId)
        when:
            handler.handle(event)
        and:
            Try<Result> result = catalogue.removeExistingAnimal(animalId.getValue())
        then:
            result.isFailure()
    }
}
