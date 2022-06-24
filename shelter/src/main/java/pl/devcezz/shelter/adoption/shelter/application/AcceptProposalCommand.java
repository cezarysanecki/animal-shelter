package pl.devcezz.shelter.adoption.shelter.application;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;

@Value
public class AcceptProposalCommand {
    @NonNull ProposalId proposalId;
}
