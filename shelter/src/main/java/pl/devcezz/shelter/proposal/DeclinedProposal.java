package pl.devcezz.shelter.proposal;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.shared.Version;

@Value
@AllArgsConstructor
public class DeclinedProposal implements Proposal {

    @NonNull
    SubjectId subjectId;

    @NonNull
    Version version;
}
