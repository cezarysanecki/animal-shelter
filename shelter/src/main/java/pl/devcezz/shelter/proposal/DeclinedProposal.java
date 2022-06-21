package pl.devcezz.shelter.proposal;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.shared.Version;

import java.util.UUID;

@Value
@AllArgsConstructor
public class DeclinedProposal implements ProposalI {

    @NonNull
    SubjectId subjectId;

    @NonNull
    Version version;

    public DeclinedProposal(UUID subjectId, int version) {
        this.subjectId = SubjectId.of(subjectId);
        this.version = new Version(version);
    }
}
