package pl.devcezz.shelter.adoption.shelter.model;

import io.vavr.collection.Set;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import pl.devcezz.shelter.adoption.proposal.model.AcceptedProposal;
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal;
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

    public Either<ProposalAcceptingFailed, ProposalAcceptedEvents> accept(PendingProposal pendingProposal) {
        if (enoughSpaceInShelterAfterAccepting()) {
            ProposalAccepted proposalAccepted = proposalAcceptedNow(pendingProposal.getProposalId());
            if (safeThresholdExceededAfterAccepting()) {
                return announceSuccess(ProposalAcceptedEvents.events(proposalAccepted, SafeThresholdExceeded.now(pendingProposal.getProposalId(), countLeftSpace())));
            }
            return announceSuccess(ProposalAcceptedEvents.events(proposalAccepted));
        }
        return announceFailure(proposalAcceptingFailedNow("no space left for animals in shelter", pendingProposal.getProposalId()));
    }

    public Either<ProposalCancelingFailed, ProposalCanceled> cancel(AcceptedProposal acceptedProposal) {
        if (acceptedProposals.contains(acceptedProposal.getProposalId())) {
            return announceSuccess(proposalCanceledNow(acceptedProposal.getProposalId()));
        }
        return announceFailure(proposalCancelingFailedNow(acceptedProposal.getProposalId()));
    }

    private long countLeftSpace() {
        return CAPACITY - (countAcceptedProposals() + 1);
    }

    private boolean enoughSpaceInShelterAfterAccepting() {
        return CAPACITY >= countAcceptedProposals() + 1;
    }

    private boolean safeThresholdExceededAfterAccepting() {
        return SAFE_THRESHOLD <= countAcceptedProposals() + 1;
    }

    private int countAcceptedProposals() {
        return acceptedProposals.length();
    }
}
