package pl.devcezz.shelter.adoption.shelter.application

import io.vavr.control.Option
import io.vavr.control.Try
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal
import pl.devcezz.shelter.adoption.shelter.model.Shelter
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent
import pl.devcezz.shelter.adoption.shelter.model.Shelters
import pl.devcezz.shelter.commons.commands.Result
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.pendingProposal
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelter
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelterWithProposals

class AcceptingProposalSpec extends Specification {

    PendingProposal pendingProposal = pendingProposal()

    FindPendingProposal willFindProposal = { proposalId -> Option.of(pendingProposal) }
    FindPendingProposal willNotFindProposal = { proposalId -> Option.none() }
    Shelters repository = Stub()

    def 'should successfully accept proposal if proposal exists and is pending'() {
        given:
            AcceptingProposal acceptingProposal = new AcceptingProposal(willFindProposal, repository)
        and:
            persisted(shelter())
        when:
            Try<Result> result = acceptingProposal.acceptProposal(a(pendingProposal))
        then:
            result.isSuccess()
            result.get() == Result.Success
    }

    def 'should reject accepting proposal if one of the domain rules is broken'() {
        given:
            AcceptingProposal acceptingProposal = new AcceptingProposal(willFindProposal, repository)
        and:
            persisted(shelterWithProposals(22))
        when:
            Try<Result> result = acceptingProposal.acceptProposal(a(pendingProposal))
        then:
            result.isSuccess()
            result.get() == Result.Rejection
    }

    def 'should fail if pending proposal does not exists'() {
        given:
            AcceptingProposal acceptingProposal = new AcceptingProposal(willNotFindProposal, repository)
        and:
            persisted(shelter())
        when:
            Try<Result> result = acceptingProposal.acceptProposal(a(pendingProposal))
        then:
            result.isFailure()
    }

    def 'should fail if accepting proposal fails'() {
        given:
            AcceptingProposal acceptingProposal = new AcceptingProposal(willFindProposal, repository)
        and:
            persistedThatFails(shelter())
        when:
            Try<Result> result = acceptingProposal.acceptProposal(a(pendingProposal))
        then:
            result.isFailure()
    }

    AcceptProposalCommand a(PendingProposal pendingProposal) {
        return new AcceptProposalCommand(pendingProposal.getProposalId())
    }

    def persisted(Shelter shelter) {
        repository.prepareShelter() >> shelter
        repository.publish(_ as ShelterEvent) >> shelter
    }

    def persistedThatFails(Shelter shelter) {
        repository.prepareShelter() >> shelter
        repository.publish(_ as ShelterEvent) >> { throw new IllegalStateException() }
    }
}
