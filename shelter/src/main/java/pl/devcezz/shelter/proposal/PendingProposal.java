package pl.devcezz.shelter.proposal;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.shared.Version;

@Value
public class PendingProposal implements Proposal {

    @NonNull
    ProposalId proposalId;

    @NonNull
    Version version;

    AcceptedProposal accept() {
        return new AcceptedProposal(proposalId, version);
    }

    DeclinedProposal decline() {
        return new DeclinedProposal(proposalId, version);
    }

    DeclinedProposal delete() {
        return new DeclinedProposal(proposalId, version);
    }
}
