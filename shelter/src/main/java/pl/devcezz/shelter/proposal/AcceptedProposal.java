package pl.devcezz.shelter.proposal;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.shared.Version;

import java.util.UUID;

@Value
@AllArgsConstructor
public class AcceptedProposal implements ProposalI {

    @NonNull
    SubjectId subjectId;

    @NonNull
    Version version;

    public AcceptedProposal(UUID subjectId, int version) {
        this.subjectId = SubjectId.of(subjectId);
        this.version = new Version(version);
    }
}
