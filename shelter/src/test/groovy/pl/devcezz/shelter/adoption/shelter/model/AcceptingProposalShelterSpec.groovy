package pl.devcezz.shelter.adoption.shelter.model

import io.vavr.collection.Set
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.commons.aggregates.Version
import spock.lang.Specification

import static io.vavr.collection.HashSet.rangeClosed
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptingFailed

class AcceptingProposalShelterSpec extends Specification {

    def 'shelter should be able to accept up to 10 proposals'() {
        given:
            PendingProposal pendingProposal = aPendingProposal()
        and:
            Shelter shelter = shelterWithProposals(proposals)
        when:
            def acceptProposal = shelter.accept(pendingProposal)
        then:
            acceptProposal.isRight()
            acceptProposal.get().with {
                ProposalAccepted proposalAccepted = it.proposalAccepted
                assert proposalAccepted.proposalId == pendingProposal.getProposalId().value
            }
        where:
            proposals << (0..9)
    }

    def 'shelter cannot accept more than 10 proposals'() {
        given:
            PendingProposal pendingProposal = aPendingProposal()
        and:
            Shelter shelter = shelterWithProposals(proposals)
        when:
            def acceptProposal = shelter.accept(pendingProposal)
        then:
            acceptProposal.isLeft()
            ProposalAcceptingFailed e = acceptProposal.getLeft()
            e.getReason().contains("no space left for animals in shelter")
        where:
            proposals << [10, 11, 200]
    }

    Shelter shelterWithProposals(int numberOfProposals) {
        new Shelter(acceptedProposals(numberOfProposals))
    }

    Set<ProposalId> acceptedProposals(int numberOfProposals) {
        rangeClosed(1, numberOfProposals)
                .map(i -> ProposalId.of(UUID.randomUUID()))
                .toSet()
    }

    PendingProposal aPendingProposal() {
        new PendingProposal(ProposalId.of(UUID.randomUUID()), Version.zero())
    }
}