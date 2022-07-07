package pl.devcezz.shelter.adoption.proposal.application

import io.vavr.control.Option
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal
import pl.devcezz.shelter.adoption.proposal.model.Proposal
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.adoption.proposal.model.Proposals
import pl.devcezz.shelter.catalogue.AnimalId
import pl.devcezz.shelter.catalogue.CatalogueEvent
import pl.devcezz.shelter.commons.events.DomainEvents
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId

class SavingProposalSpec extends Specification {

    DomainEvents publisher = Mock()
    Proposals repository = new InMemoryProposalRepository()
    ProposalApplicationConfig config = new ProposalApplicationConfig(repository, publisher)

    CatalogueOperationsEventsHandler handler = config.catalogueOperationsEventsHandler()

    def 'should save pending proposal'() {
        given:
            ProposalId proposalId = anyProposalId()
        when:
            handler.handle(
                    CatalogueEvent.AnimalConfirmedEvent.animalConfirmedNow(AnimalId.of(proposalId.getValue())))
        then:
            proposalIsPersistedAs(PendingProposal.class, proposalId)
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
