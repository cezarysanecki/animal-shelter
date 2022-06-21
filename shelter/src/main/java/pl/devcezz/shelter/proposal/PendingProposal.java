package pl.devcezz.shelter.proposal;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.shared.Version;

import java.util.UUID;

@Value
public class PendingProposal implements Proposal {

    @NonNull
    SubjectId subjectId;

    @NonNull
    Version version;

    public PendingProposal(UUID subjectId, int version) {
        this.subjectId = SubjectId.of(subjectId);
        this.version = new Version(version);
    }

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
