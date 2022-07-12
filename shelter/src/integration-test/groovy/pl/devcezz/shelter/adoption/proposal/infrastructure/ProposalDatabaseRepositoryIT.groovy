package pl.devcezz.shelter.adoption.proposal.infrastructure

import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.devcezz.shelter.adoption.proposal.ProposalTestContext
import pl.devcezz.shelter.adoption.proposal.model.*
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.*

@SpringBootTest(classes = ProposalTestContext.class)
class ProposalDatabaseRepositoryIT extends Specification {

    @Autowired
    ProposalDatabaseRepository proposalEntityRepository

    def 'persistence in real database should work for accepted proposal'() {
        given: "Prepare accepted proposal."
            AcceptedProposal acceptedProposal = acceptedProposal()
        when: "Save accepted proposal."
            proposalEntityRepository.save(acceptedProposal)
        then: "Proposal is stored as accepted."
            proposalIsPersistedAs(AcceptedProposal.class, acceptedProposal.getProposalId())
    }

    def 'persistence in real database should work for pending proposal'() {
        given: "Prepare pending proposal."
            PendingProposal pendingProposal = pendingProposal()
        when: "Save pending proposal."
            proposalEntityRepository.save(pendingProposal)
        then: "Proposal is stored as pending."
            proposalIsPersistedAs(PendingProposal.class, pendingProposal.getProposalId())
    }

    def 'persistence in real database should work for deleted proposal'() {
        given: "Prepare deleted proposal."
            DeletedProposal deletedProposal = deletedProposal()
        when: "Save deleted proposal."
            proposalEntityRepository.save(deletedProposal)
        then: "Proposal is stored as deleted."
            proposalIsPersistedAs(DeletedProposal.class, deletedProposal.getProposalId())
    }

    void proposalIsPersistedAs(Class<?> clazz, ProposalId proposalId) {
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