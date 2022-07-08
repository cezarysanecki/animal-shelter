package pl.devcezz.shelter.adoption.proposal.model

import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.acceptedProposal
import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.pendingProposal

class ProposalStateSpec extends Specification {

    def 'should accept proposal when pending'() {
        given: "Prepare pending proposal."
            PendingProposal pendingProposal = pendingProposal()
        when: "Accept proposal."
            def result = pendingProposal.accept()
        then: "Proposal is accepted."
            result instanceof AcceptedProposal
    }

    def 'should delete proposal when pending'() {
        given: "Prepare pending proposal."
            PendingProposal pendingProposal = pendingProposal()
        when: "Delete proposal."
            def result = pendingProposal.delete()
        then: "Proposal is deleted."
            result instanceof DeletedProposal
    }

    def 'should cancel proposal when accepted'() {
        given: "Prepare accepted proposal."
            AcceptedProposal acceptedProposal = acceptedProposal()
        when: "Cancel proposal."
            def result = acceptedProposal.cancel()
        then: "Proposal is pending."
            result instanceof PendingProposal
    }
}
