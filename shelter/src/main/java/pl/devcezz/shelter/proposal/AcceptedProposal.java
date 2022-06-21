package pl.devcezz.shelter.proposal;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.shared.Version;

@Value
@AllArgsConstructor
public class AcceptedProposal implements Proposal {

    @NonNull
    ProposalId proposalId;

    @NonNull
    Version version;
}
