package pl.devcezz.shelter.adoption.shelter.model;

import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal;

import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptedEvents.events;
import static pl.devcezz.shelter.shared.event.EitherResult.announceFailure;
import static pl.devcezz.shelter.shared.event.EitherResult.announceSuccess;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Shelter {

    private static final long CAPACITY = 10;
    private static final long SAFE_THRESHOLD = 7;

    @NonNull
    private final long acceptedProposalsCount;

    public Either<ShelterEvent.ProposalAcceptedFailed, ShelterEvent.ProposalAcceptedEvents> accept(PendingProposal pendingProposal) {
        if (enoughSpaceInShelterAfterAccepting()) {
            ShelterEvent.ProposalAccepted proposalAccepted = ShelterEvent.ProposalAccepted.proposalAcceptedNow(pendingProposal.getProposalId());
            if (safeThresholdExceededAfterAccepting()) {
                return announceSuccess(ShelterEvent.ProposalAcceptedEvents.events(proposalAccepted, ShelterEvent.SafeThresholdExceeded.now(pendingProposal.getProposalId(), countLeftSpace())));
            }
            return announceSuccess(ShelterEvent.ProposalAcceptedEvents.events(proposalAccepted));
        }
        return announceFailure(ShelterEvent.ProposalAcceptedFailed.proposalAcceptedFailedNow("no space left for animals in shelter", pendingProposal.getProposalId()));
    }

    private long countLeftSpace() {
        return CAPACITY - (acceptedProposalsCount + 1);
    }

    private boolean enoughSpaceInShelterAfterAccepting() {
        return CAPACITY >= acceptedProposalsCount + 1;
    }

    private boolean safeThresholdExceededAfterAccepting() {
        return SAFE_THRESHOLD <= acceptedProposalsCount + 1;
    }
}
