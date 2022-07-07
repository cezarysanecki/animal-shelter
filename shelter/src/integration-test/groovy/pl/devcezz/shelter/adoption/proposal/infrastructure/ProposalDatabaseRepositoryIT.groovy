package pl.devcezz.shelter.adoption.proposal.infrastructure

import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.devcezz.shelter.adoption.AdoptionTestContext
import pl.devcezz.shelter.adoption.proposal.model.*
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.*

@SpringBootTest(classes = AdoptionTestContext.class)
class ProposalDatabaseRepositoryIT extends Specification {

    @Autowired
    ProposalDatabaseRepository proposalEntityRepository

    def 'persistence in real database should work for accepted proposal'() {
        given:
            AcceptedProposal acceptedProposal = acceptedProposal()
        when:
            proposalEntityRepository.save(acceptedProposal)
        then:
            proposalIsPersistedAs(AcceptedProposal.class, acceptedProposal.getProposalId())
    }

    def 'persistence in real database should work for pending proposal'() {
        given:
            PendingProposal pendingProposal = pendingProposal()
        when:
            proposalEntityRepository.save(pendingProposal)
        then:
            proposalIsPersistedAs(PendingProposal.class, pendingProposal.getProposalId())
    }

    def 'persistence in real database should work for deleted proposal'() {
        given:
            DeletedProposal deletedProposal = deletedProposal()
        when:
            proposalEntityRepository.save(deletedProposal)
        then:
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