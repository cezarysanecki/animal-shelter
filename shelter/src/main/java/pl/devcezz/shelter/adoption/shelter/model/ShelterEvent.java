package pl.devcezz.shelter.adoption.shelter.model;

import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.commons.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public interface ShelterEvent extends DomainEvent {

    default ProposalId proposalId() {
        return ProposalId.of(getProposalId());
    }

    UUID getProposalId();

    @Value
    class ProposalAccepted implements ShelterEvent {
        @NonNull UUID eventId = UUID.randomUUID();
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
        @NonNull UUID eventId = UUID.randomUUID();
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
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull UUID proposalId;
        @NonNull ProposalAccepted proposalAccepted;
        @NonNull Option<SafeThresholdExceeded> safeThresholdExceeded;

        @Override
        public Instant getWhen() {
            return proposalAccepted.when;
        }

        public static ProposalAcceptedEvents events(ProposalAccepted proposalAccepted) {
            return new ProposalAcceptedEvents(proposalAccepted.getProposalId(), proposalAccepted, Option.none());
        }

        public static ProposalAcceptedEvents events(ProposalAccepted proposalAccepted, SafeThresholdExceeded safeThresholdExceeded) {
            return new ProposalAcceptedEvents(proposalAccepted.getProposalId(), proposalAccepted, Option.of(safeThresholdExceeded));
        }

        @Override
        public List<DomainEvent> normalize() {
            return List.<DomainEvent>of(proposalAccepted).appendAll(safeThresholdExceeded.toList());
        }
    }

    @Value
    class ProposalAcceptingFailed implements ShelterEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull String reason;
        @NonNull Instant when;
        @NonNull UUID proposalId;

        static ProposalAcceptingFailed proposalAcceptingFailedNow(String reason, ProposalId proposalId) {
            return new ProposalAcceptingFailed(
                    reason,
                    Instant.now(),
                    proposalId.getValue());
        }
    }

    @Value
    class ProposalCanceled implements ShelterEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull Instant when;
        @NonNull UUID proposalId;

        public static ProposalCanceled proposalCanceledNow(ProposalId proposalId) {
            return new ProposalCanceled(
                    Instant.now(),
                    proposalId.getValue());
        }
    }

    @Value
    class ProposalCancelingFailed implements ShelterEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull Instant when;
        @NonNull UUID proposalId;

        static ProposalCancelingFailed proposalCancelingFailedNow(ProposalId proposalId) {
            return new ProposalCancelingFailed(
                    Instant.now(),
                    proposalId.getValue());
        }
    }

    @Value
    class ProposalConfirmed implements ShelterEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull Instant when;
        @NonNull UUID proposalId;

        public static ProposalConfirmed proposalConfirmedNow(ProposalId proposalId) {
            return new ProposalConfirmed(
                    Instant.now(),
                    proposalId.getValue());
        }
    }
}
