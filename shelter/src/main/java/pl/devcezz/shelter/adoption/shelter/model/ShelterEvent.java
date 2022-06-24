package pl.devcezz.shelter.adoption.shelter.model;

import io.vavr.control.Option;
import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;

import java.time.Instant;
import java.util.UUID;

public interface ShelterEvent {

    @Value
    class ProposalAccepted implements ShelterEvent {
        @NonNull Instant when;
        @NonNull UUID proposalId;

        public static ProposalAccepted proposalAcceptedNow(ProposalId proposalId) {
            return new ProposalAccepted(
                    Instant.now(),
                    proposalId.getValue());
        }
    }

    @Value
    class SafeThresholdExceeded implements ShelterEvent {
        @NonNull Instant when;
        @NonNull UUID proposalId;
        long spaceLeft;

        public static SafeThresholdExceeded now(ProposalId proposalId, long spaceLeft) {
            return new SafeThresholdExceeded(
                    Instant.now(),
                    proposalId.getValue(),
                    spaceLeft);
        }
    }

    @Value
    class ProposalAcceptedEvents implements ShelterEvent {
        @NonNull UUID proposalId;
        @NonNull ProposalAccepted proposalAccepted;
        @NonNull Option<SafeThresholdExceeded> safeThresholdExceeded;

        public static ProposalAcceptedEvents events(ProposalAccepted proposalAccepted) {
            return new ProposalAcceptedEvents(proposalAccepted.getProposalId(), proposalAccepted, Option.none());
        }

        public static ProposalAcceptedEvents events(ProposalAccepted proposalAccepted, SafeThresholdExceeded safeThresholdExceeded) {
            return new ProposalAcceptedEvents(proposalAccepted.getProposalId(), proposalAccepted, Option.of(safeThresholdExceeded));
        }
    }

    @Value
    class ProposalAcceptedFailed implements ShelterEvent {
        @NonNull String reason;
        @NonNull Instant when;
        @NonNull UUID proposalId;

        static ProposalAcceptedFailed proposalAcceptedFailedNow(String reason, ProposalId proposalId) {
            return new ProposalAcceptedFailed(
                    reason,
                    Instant.now(),
                    proposalId.getValue());
        }
    }
}
