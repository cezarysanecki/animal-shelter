package pl.devcezz.shelter.adoption.proposal.application

import io.vavr.control.Option
import pl.devcezz.shelter.adoption.proposal.model.Proposal
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.adoption.proposal.model.Proposals
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent
import pl.devcezz.shelter.catalogue.AnimalId
import pl.devcezz.shelter.commons.events.DomainEvents
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAcceptanceFailed
import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted.proposalAcceptedNow
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled.proposalCanceledNow
import static pl.devcezz.shelter.catalogue.CatalogueEvent.AnimalConfirmedEvent.animalConfirmedNow

class HandleProposalProcessSpec extends Specification {

    DomainEvents publisher = Mock()
    Proposals repository = new InMemoryProposalRepository()
    ProposalApplicationConfig config = new ProposalApplicationConfig(repository, publisher)

    CatalogueOperationsEventsHandler catalogueHandler = config.catalogueOperationsEventsHandler()
    ShelterOperationsEventsHandler shelterHandler = config.shelterOperationsEventHandler()

    def 'should handle proposal process'() {
        given: "Prepare any proposal id."
            ProposalId proposalId = anyProposalId()
        when: "Animal has been confirmed."
            catalogueHandler.handle(animalConfirmedNow(AnimalId.of(proposalId.getValue())))
        and: "Proposal is accepted."
            shelterHandler.handle(proposalAcceptedNow(proposalId))
        and: "Proposal is canceled."
            shelterHandler.handle(proposalCanceledNow(proposalId))
        and: "Proposal is accepted once again."
            shelterHandler.handle(proposalAcceptedNow(proposalId))
        and: "Proposal is accepted one more time."
            shelterHandler.handle(proposalAcceptedNow(proposalId))
        then: "Operation of accepting the same proposal is forbidden."
            1 * publisher.publish(_ as ProposalAcceptanceFailed)
    }

    void proposalIsPersistedAs(Class<?> clazz, ProposalId proposalId) {
        Proposal proposal = loadPersistedProposal(proposalId)
        assert proposal.class == clazz
    }

    Proposal loadPersistedProposal(ProposalId proposalId) {
        Option<Proposal> loaded = repository.findBy(proposalId)
        Proposal proposal = loaded.getOrElseThrow({
            new IllegalStateException("should have been persisted")
        })
        return proposal
    }
}
