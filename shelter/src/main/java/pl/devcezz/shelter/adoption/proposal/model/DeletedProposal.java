package pl.devcezz.shelter.adoption.proposal.model;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.shared.Version;

@Value
@AllArgsConstructor
public class DeletedProposal implements Proposal {

    @NonNull
    ProposalId proposalId;

    @NonNull
    Version version;
}
