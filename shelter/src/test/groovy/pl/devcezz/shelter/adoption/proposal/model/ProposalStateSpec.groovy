package pl.devcezz.shelter.adoption.proposal.model


import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.acceptedProposal
import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.pendingProposal

class ProposalStateSpec extends Specification {

    def 'should accept proposal when pending'() {
        given:
            PendingProposal pendingProposal = pendingProposal()
        when:
            def result = pendingProposal.accept()
        then:
            result instanceof AcceptedProposal
    }

    def 'should delete proposal when pending'() {
        given:
            PendingProposal pendingProposal = pendingProposal()
        when:
            def result = pendingProposal.delete()
        then:
            result instanceof DeletedProposal
    }

    def 'should cancel proposal when accepted'() {
        given:
            AcceptedProposal acceptedProposal = acceptedProposal()
        when:
            def result = acceptedProposal.cancel()
        then:
            result instanceof PendingProposal
    }
}
