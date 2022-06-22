package pl.devcezz.shelter.shelter.application;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.proposal.model.ProposalId;

@Value
public class AcceptProposalCommand {
    @NonNull ProposalId proposalId;
}
