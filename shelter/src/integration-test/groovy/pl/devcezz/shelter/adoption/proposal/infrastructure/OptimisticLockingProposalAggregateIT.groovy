package pl.devcezz.shelter.adoption.proposal.infrastructure

import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.devcezz.shelter.adoption.proposal.ProposalTestContext
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal
import pl.devcezz.shelter.adoption.proposal.model.Proposal
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.commons.aggregates.AggregateRootIsStale
import pl.devcezz.shelter.commons.aggregates.Version
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.pendingProposal

@SpringBootTest(classes = ProposalTestContext.class)
class OptimisticLockingProposalAggregateIT extends Specification {

    ProposalId proposalId = anyProposalId()

    @Autowired
    ProposalDatabaseRepository proposalEntityRepository

    def 'persistence in real database should work'() {
        given: "Prepare pending proposal."
            PendingProposal pendingProposal = pendingProposal(proposalId)
        and: "Save proposal."
            proposalEntityRepository.save(pendingProposal)
        and: "Load persisted proposal."
            Proposal loaded = loadPersistedProposal(proposalId)
        and: "Accept proposal in the meantime."
            someoneAcceptedProposalInTheMeantime(pendingProposal)
        when: "Save previous version of proposal."
            proposalEntityRepository.save(loaded)
        then: "Optimistic locking mechanism worked."
            thrown(AggregateRootIsStale)
            loadPersistedProposal(proposalId).version == new Version(1)
    }

    void someoneAcceptedProposalInTheMeantime(PendingProposal pendingProposal) {
        proposalEntityRepository.save(pendingProposal.accept())
    }

    void bookIsPersistedAs(Class<?> clazz, ProposalId proposalId) {
        Proposal proposal = loadPersistedProposal(proposalId)
        assert proposal.class == clazz
    }

    Proposal loadPersistedProposal(ProposalId proposalId) {
        Option<Proposal> loaded = proposalEntityRepository.findBy(proposalId)
        Proposal proposal = loaded.getOrElseThrow({
            new IllegalStateException("should have been persisted")
        })
        return proposal
    }
}
