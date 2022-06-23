package pl.devcezz.shelter.shelter.model;

import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import pl.devcezz.shelter.proposal.model.PendingProposal;
import pl.devcezz.shelter.shelter.model.ShelterEvent.ProposalAcceptedEvents;
import pl.devcezz.shelter.shelter.model.ShelterEvent.ProposalAcceptedFailed;
import pl.devcezz.shelter.shelter.model.ShelterEvent.SafeThresholdExceeded;

import static pl.devcezz.shelter.shelter.model.ShelterEvent.ProposalAccepted;
import static pl.devcezz.shelter.shelter.model.ShelterEvent.ProposalAccepted.*;
import static pl.devcezz.shelter.shelter.model.ShelterEvent.ProposalAcceptedEvents.events;
import static pl.devcezz.shelter.shelter.model.ShelterEvent.ProposalAcceptedFailed.proposalAcceptedFailedNow;
import static pl.devcezz.shelter.shared.event.EitherResult.announceFailure;
import static pl.devcezz.shelter.shared.event.EitherResult.announceSuccess;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Shelter {

    private static final long CAPACITY = 10;
    private static final long SAFE_THRESHOLD = 7;

    @NonNull
    private final long acceptedProposalsCount;

    public Either<ProposalAcceptedFailed, ProposalAcceptedEvents> accept(PendingProposal pendingProposal) {
        if (enoughSpaceInShelterAfterAccepting()) {
            ProposalAccepted proposalAccepted = proposalAcceptedNow(pendingProposal.getProposalId());
            if (safeThresholdExceededAfterAccepting()) {
                return announceSuccess(events(proposalAccepted, SafeThresholdExceeded.now(pendingProposal.getProposalId(), countLeftSpace())));
            }
            return announceSuccess(events(proposalAccepted));
        }
        return announceFailure(proposalAcceptedFailedNow("no space left for animals in shelter", pendingProposal.getProposalId()));
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
