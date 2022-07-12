package pl.devcezz.shelter.adoption.shelter.model;

import io.vavr.collection.Set;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;

import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted.proposalAcceptedNow;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptedEvents;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptingFailed;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptingFailed.proposalAcceptingFailedNow;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled.ProposalCanceled;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled.ProposalCancelingFailed;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled.proposalCanceledNow;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCancelingFailed.proposalCancelingFailedNow;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.SafeThresholdExceeded;
import static pl.devcezz.shelter.commons.events.EitherResult.announceFailure;
import static pl.devcezz.shelter.commons.events.EitherResult.announceSuccess;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Shelter {

    static final long CAPACITY = 10;
    static final long SAFE_THRESHOLD = 7;

    @NonNull
    private final Set<ProposalId> acceptedProposals;
    @NonNull
    private final Set<ProposalId> pendingProposals;

    public Either<ProposalAcceptingFailed, ProposalAcceptedEvents> accept(ProposalId proposalId) {
        if (acceptedProposals.contains(proposalId)) {
            return announceFailure(proposalAcceptingFailedNow("proposal is already accepted", proposalId));
        }
        if (pendingProposals.contains(proposalId)) {
            return announceFailure(proposalAcceptingFailedNow("proposal is pending", proposalId));
        }

        if (enoughSpaceInShelterAfterAccepting()) {
            ProposalAccepted proposalAccepted = proposalAcceptedNow(proposalId);
            if (safeThresholdExceededAfterAccepting()) {
                return announceSuccess(ProposalAcceptedEvents.events(proposalAccepted, SafeThresholdExceeded.now(proposalId, countLeftSpace())));
            }
            return announceSuccess(ProposalAcceptedEvents.events(proposalAccepted));
        }
        return announceFailure(proposalAcceptingFailedNow("no space left for animals in shelter", proposalId));
    }

    public Either<ProposalCancelingFailed, ProposalCanceled> cancel(ProposalId proposalId) {
        if (acceptedProposals.contains(proposalId)) {
            return announceSuccess(proposalCanceledNow(proposalId));
        }
        return announceFailure(proposalCancelingFailedNow(proposalId));
    }

    public int numberOfProposals() {
        return acceptedProposals.length() + pendingProposals.length();
    }

    private long countLeftSpace() {
        return CAPACITY - (numberOfProposals() + 1);
    }

    private boolean enoughSpaceInShelterAfterAccepting() {
        return CAPACITY >= numberOfProposals() + 1;
    }

    private boolean safeThresholdExceededAfterAccepting() {
        return SAFE_THRESHOLD <= numberOfProposals() + 1;
    }
}
