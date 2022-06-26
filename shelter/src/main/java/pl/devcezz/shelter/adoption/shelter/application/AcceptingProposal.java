package pl.devcezz.shelter.adoption.shelter.application;

import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.shelter.model.Shelter;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptedEvents;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptingFailed;
import pl.devcezz.shelter.adoption.shelter.model.Shelters;
import pl.devcezz.shelter.commons.commands.Result;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static pl.devcezz.shelter.commons.commands.Result.Rejection;
import static pl.devcezz.shelter.commons.commands.Result.Success;

@RequiredArgsConstructor
public class AcceptingProposal {

    private final FindPendingProposal findPendingProposal;
    private final Shelters shelterRepository;

    public Try<Result> acceptProposal(@NonNull AcceptProposalCommand command) {
        return Try.of(() -> {
            PendingProposal pendingProposal = find(command.getProposalId());
            Shelter shelter = prepare();
            Either<ProposalAcceptingFailed, ProposalAcceptedEvents> result = shelter.accept(pendingProposal);
            return Match(result).of(
                    Case($Left($()), this::publishEvents),
                    Case($Right($()), this::publishEvents)
            );
        });
    }

    private Result publishEvents(ProposalAcceptingFailed proposalAcceptingFailed) {
        shelterRepository.publish(proposalAcceptingFailed);
        return Rejection;
    }

    private Result publishEvents(ProposalAcceptedEvents proposalAcceptedEvents) {
        shelterRepository.publish(proposalAcceptedEvents);
        return Success;
    }

    private PendingProposal find(ProposalId proposalId) {
        return findPendingProposal
                .findPendingProposalBy(proposalId)
                .getOrElseThrow(() -> new IllegalArgumentException("cannot find proposal with id: " + proposalId.getValue()));
    }

    private Shelter prepare() {
        return shelterRepository.prepareShelter();
    }
}
