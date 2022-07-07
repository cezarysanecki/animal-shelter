package pl.devcezz.shelter.adoption.shelter.application

import io.vavr.control.Option
import io.vavr.control.Try
import pl.devcezz.shelter.adoption.proposal.model.AcceptedProposal
import pl.devcezz.shelter.adoption.shelter.model.Shelter
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent
import pl.devcezz.shelter.adoption.shelter.model.Shelters
import pl.devcezz.shelter.commons.commands.Result
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.acceptedProposal
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelter
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelterWithProposal

class CancelingProposalSpec extends Specification {

    AcceptedProposal acceptedProposal = acceptedProposal(anyProposalId())

    FindAcceptedProposal willFindProposal = { proposalId -> Option.of(acceptedProposal) }
    FindAcceptedProposal willNotFindProposal = { proposalId -> Option.none() }
    Shelters repository = Stub()

    def 'should successfully cancel proposal if proposal exists and is accepted'() {
        given:
            CancelingProposal cancelingProposal = new CancelingProposal(willFindProposal, repository)
        and:
            persisted(shelterWithProposal(acceptedProposal))
        when:
            Try<Result> result = cancelingProposal.cancelProposal(a(acceptedProposal))
        then:
            result.isSuccess()
            result.get() == Result.Success
    }

    def 'should reject canceling proposal if one of the domain rules is broken'() {
        given:
            CancelingProposal cancelingProposal = new CancelingProposal(willFindProposal, repository)
        and:
            persisted(shelter())
        when:
            Try<Result> result = cancelingProposal.cancelProposal(a(acceptedProposal))
        then:
            result.isSuccess()
            result.get() == Result.Rejection
    }

    def 'should fail if accepted proposal does not exists'() {
        given:
            CancelingProposal cancelingProposal = new CancelingProposal(willNotFindProposal, repository)
        and:
            persisted(shelterWithProposal(acceptedProposal))
        when:
            Try<Result> result = cancelingProposal.cancelProposal(a(acceptedProposal))
        then:
            result.isFailure()
    }

    def 'should fail if canceling proposal fails'() {
        given:
            CancelingProposal cancelingProposal = new CancelingProposal(willNotFindProposal, repository)
        and:
            persistedThatFails(shelterWithProposal(acceptedProposal))
        when:
            Try<Result> result = cancelingProposal.cancelProposal(a(acceptedProposal))
        then:
            result.isFailure()
    }

    CancelProposalCommand a(AcceptedProposal acceptedProposal) {
        return new CancelProposalCommand(acceptedProposal.getProposalId())
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
