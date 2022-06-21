package pl.devcezz.shelter.proposal;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.shared.Version;

import java.util.UUID;

@Value
@AllArgsConstructor
public class DeletedProposal implements Proposal {

    @NonNull
    SubjectId subjectId;

    @NonNull
    Version version;

    public DeletedProposal(UUID subjectId, int version) {
        this.subjectId = SubjectId.of(subjectId);
        this.version = new Version(version);
    }
}
