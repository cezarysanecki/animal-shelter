package pl.devcezz.shelter.shelter.application;

import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.proposal.model.PendingProposal;
import pl.devcezz.shelter.proposal.model.ProposalId;
import pl.devcezz.shelter.shelter.model.Shelter;
import pl.devcezz.shelter.shelter.model.Shelters;
import pl.devcezz.shelter.shared.Result;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static pl.devcezz.shelter.shelter.model.ShelterEvent.ProposalAcceptedEvents;
import static pl.devcezz.shelter.shelter.model.ShelterEvent.ProposalAcceptedFailed;
import static pl.devcezz.shelter.shared.Result.Rejection;
import static pl.devcezz.shelter.shared.Result.Success;

@RequiredArgsConstructor
public class AcceptingProposal {

    private final FindPendingProposal findPendingProposal;
    private final Shelters shelterRepository;

    public Try<Result> acceptProposal(@NonNull AcceptProposalCommand command) {
        return Try.of(() -> {
            PendingProposal pendingProposal = find(command.getProposalId());
            Shelter shelter = prepare();
            Either<ProposalAcceptedFailed, ProposalAcceptedEvents> result = shelter.accept(pendingProposal);
            return Match(result).of(
                    Case($Left($()), this::publishEvents),
                    Case($Right($()), this::publishEvents)
            );
        });
    }

    private Result publishEvents(ProposalAcceptedFailed proposalAcceptedFailed) {
        shelterRepository.publish(proposalAcceptedFailed);
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
