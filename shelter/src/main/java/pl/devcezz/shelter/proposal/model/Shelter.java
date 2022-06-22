package pl.devcezz.shelter.proposal.model;

import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import pl.devcezz.shelter.proposal.model.ShelterEvent.ProposalAcceptedEvents;
import pl.devcezz.shelter.proposal.model.ShelterEvent.ProposalAcceptedFailed;
import pl.devcezz.shelter.proposal.model.ShelterEvent.SafeThresholdExceeded;

import static pl.devcezz.shelter.proposal.model.ShelterEvent.ProposalAccepted;
import static pl.devcezz.shelter.proposal.model.ShelterEvent.ProposalAccepted.*;
import static pl.devcezz.shelter.proposal.model.ShelterEvent.ProposalAcceptedEvents.events;
import static pl.devcezz.shelter.proposal.model.ShelterEvent.ProposalAcceptedFailed.proposalAcceptedFailedNow;
import static pl.devcezz.shelter.shared.event.EitherResult.announceFailure;
import static pl.devcezz.shelter.shared.event.EitherResult.announceSuccess;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Shelter {

    private static final int CAPACITY = 10;
    private static final int SAFE_THRESHOLD = 7;

    @NonNull
    private final int acceptedProposalsCount;

    public Either<ProposalAcceptedFailed, ProposalAcceptedEvents> accept(PendingProposal pendingProposal) {
        if (enoughSpaceInShelterAfterAccepting()) {
            ProposalAccepted proposalAccepted = proposalAcceptedNow(pendingProposal.getProposalId());
            if (safeThresholdExceededAfterAccepting()) {
                return announceSuccess(events(proposalAccepted, SafeThresholdExceeded.now(pendingProposal.getProposalId(), countSpaceLeft())));
            }
            return announceSuccess(events(proposalAccepted));
        }
        return announceFailure(proposalAcceptedFailedNow("no space left for animals in shelter", pendingProposal.getProposalId()));
    }

    private int countSpaceLeft() {
        return CAPACITY - (acceptedProposalsCount + 1);
    }

    private boolean enoughSpaceInShelterAfterAccepting() {
        return CAPACITY >= acceptedProposalsCount + 1;
    }

    private boolean safeThresholdExceededAfterAccepting() {
        return SAFE_THRESHOLD <= acceptedProposalsCount + 1;
    }
}
