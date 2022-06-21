package pl.devcezz.shelter.proposal.model;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.shared.Version;

@Value
public class PendingProposal implements Proposal {

    @NonNull
    ProposalId proposalId;

    @NonNull
    Version version;

    public AcceptedProposal accept() {
        return new AcceptedProposal(proposalId, version);
    }

    public DeclinedProposal decline() {
        return new DeclinedProposal(proposalId, version);
    }

    public DeclinedProposal delete() {
        return new DeclinedProposal(proposalId, version);
    }
}
