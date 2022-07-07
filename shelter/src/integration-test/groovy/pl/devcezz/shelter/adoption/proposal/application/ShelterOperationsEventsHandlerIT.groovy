package pl.devcezz.shelter.adoption.proposal.application

import io.vavr.control.Option
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.devcezz.shelter.adoption.AdoptionTestContext
import pl.devcezz.shelter.adoption.proposal.model.*
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.acceptedProposal
import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.pendingProposal
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted.proposalAcceptedNow
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled.proposalCanceledNow

@SpringBootTest(classes = AdoptionTestContext.class)
class ShelterOperationsEventsHandlerIT extends Specification {

    @Autowired
    Proposals proposalRepository

    @Autowired
    ShelterOperationsEventsHandler eventHandler

    def 'should accept proposal'() {
        given:
            def proposalId = ProposalFixture.anyProposalId()
        and:
            proposalRepository.save(pendingProposal(proposalId))
        when:
            eventHandler.handle(proposalAcceptedNow(proposalId))
        then:
            proposalIsPersistedAs(AcceptedProposal.class, proposalId)
    }

    def 'should delete proposal after deleting new animal'() {
        given:
            def proposalId = ProposalFixture.anyProposalId()
        and:
            proposalRepository.save(acceptedProposal(proposalId))
        when:
            eventHandler.handle(proposalCanceledNow(proposalId))
        then:
            proposalIsPersistedAs(PendingProposal.class, proposalId)
    }

    void proposalIsPersistedAs(Class<?> clazz, ProposalId proposalId) {
        Proposal proposal = loadPersistedProposal(proposalId)
        assert proposal.class == clazz
    }

    Proposal loadPersistedProposal(ProposalId proposalId) {
        Option<Proposal> loaded = proposalRepository.findBy(proposalId)
        Proposal proposal = loaded.getOrElseThrow({
            new IllegalStateException("should have been persisted")
        })
        return proposal
    }

}
