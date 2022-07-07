package pl.devcezz.shelter.adoption.shelter.application

import io.vavr.control.Try
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.commons.commands.Result
import spock.lang.Specification

import java.util.stream.IntStream

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId

class ProposalProcessSpec extends Specification {

    ShelterApplicationConfig config = new ShelterApplicationConfig(new InMemoryShelterRepository())

    CancelingProposal cancelingProposal = config.cancelingProposal()
    AcceptingProposal acceptingProposal = config.acceptingProposal()

    def 'should process accepting and cancelling proposals'() {
        when:
            Try<Result> cancellationResult = cancelingProposal.cancelProposal(aCancelCommand(anyProposalId()))
        then:
            cancellationResult.isSuccess()
            cancellationResult.get() == Result.Rejection
        when:
            ProposalId acceptedProposalId = anyProposalId()
        and:
            Try<Result> acceptationResult = acceptingProposal.acceptProposal(anAcceptCommand(acceptedProposalId))
        then:
            acceptationResult.isSuccess()
            acceptationResult.get() == Result.Success
        when:
            acceptProposals(9)
        and:
            Try<Result> secondAcceptationResult = acceptingProposal.acceptProposal(anAcceptCommand(anyProposalId()))
        then:
            secondAcceptationResult.isSuccess()
            secondAcceptationResult.get() == Result.Rejection
        when:
            Try<Result> secondCancellationResult = cancelingProposal.cancelProposal(aCancelCommand(acceptedProposalId))
        then:
            secondCancellationResult.isSuccess()
            secondCancellationResult.get() == Result.Success
    }

    CancelProposalCommand aCancelCommand(ProposalId proposalId) {
        return new CancelProposalCommand(proposalId)
    }

    AcceptProposalCommand anAcceptCommand(ProposalId proposalId) {
        return new AcceptProposalCommand(proposalId)
    }

    void acceptProposals(int numberOfProposals) {
        IntStream.range(0, numberOfProposals)
                .forEach(index -> acceptingProposal.acceptProposal(anAcceptCommand(anyProposalId())))
    }
}
