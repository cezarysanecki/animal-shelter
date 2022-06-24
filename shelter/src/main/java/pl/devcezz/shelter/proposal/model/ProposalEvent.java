package pl.devcezz.shelter.proposal.model;

import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

public interface ProposalEvent {

    @Value
    class ProposalAlreadyProcessed implements ProposalEvent {
        @NonNull Instant when;
        @NonNull UUID proposalId;

        public static ProposalAlreadyProcessed proposalAlreadyProcessedNow(ProposalId proposalId) {
            return new ProposalAlreadyProcessed(
                    Instant.now(),
                    proposalId.getValue());
        }
    }
}
