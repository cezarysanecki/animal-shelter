package pl.devcezz.shelter.adoption.proposal.model;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.commons.aggregates.Version;

@Value
public class PendingProposal implements Proposal {

    @NonNull
    ProposalId proposalId;

    @NonNull
    Version version;

    public static PendingProposal createNew(ProposalId proposalId) {
        return new PendingProposal(proposalId, Version.zero());
    }

    public AcceptedProposal accept() {
        return new AcceptedProposal(proposalId, version);
    }

    public DeletedProposal delete() {
        return new DeletedProposal(proposalId, version);
    }
}
