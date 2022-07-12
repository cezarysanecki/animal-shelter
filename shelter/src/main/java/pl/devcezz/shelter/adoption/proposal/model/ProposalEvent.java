package pl.devcezz.shelter.adoption.proposal.model;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.commons.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public interface ProposalEvent extends DomainEvent {

    default ProposalId proposalId() {
        return ProposalId.of(getProposalId());
    }

    UUID getProposalId();

    @Value
    class ProposalAcceptanceFailed implements ProposalEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull String reason;
        @NonNull Instant when;
        @NonNull UUID proposalId;

        public static ProposalAcceptanceFailed proposalAcceptanceFailedNow(String reason, ProposalId proposalId) {
            return new ProposalAcceptanceFailed(
                    reason,
                    Instant.now(),
                    proposalId.getValue());
        }
    }

    @Value
    class ProposalAcceptanceConfirmed implements ProposalEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull Instant when;
        @NonNull UUID proposalId;

        public static ProposalAcceptanceConfirmed proposalAcceptanceConfirmedNow(ProposalId proposalId) {
            return new ProposalAcceptanceConfirmed(
                    Instant.now(),
                    proposalId.getValue());
        }
    }
}
