package pl.devcezz.shelter.adoption.proposal.model;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.commons.aggregates.Version;

@Value
@AllArgsConstructor
public class AcceptedProposal implements Proposal {

    @NonNull
    ProposalId proposalId;

    @NonNull
    Version version;
}
