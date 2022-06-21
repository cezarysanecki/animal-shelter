package pl.devcezz.shelter.proposal;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.shared.Version;

@Value
public class PendingProposal implements Proposal {

    @NonNull
    SubjectId subjectId;

    @NonNull
    Version version;

    AcceptedProposal accept() {
        return new AcceptedProposal(subjectId, version);
    }

    DeclinedProposal decline() {
        return new DeclinedProposal(subjectId, version);
    }

    DeclinedProposal delete() {
        return new DeclinedProposal(subjectId, version);
    }
}
