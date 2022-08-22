package pl.devcezz.shelter.adoption.proposal.view;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.commons.model.Age;
import pl.devcezz.shelter.commons.model.Gender;
import pl.devcezz.shelter.commons.model.Name;
import pl.devcezz.shelter.commons.model.Species;

@Value(staticConstructor = "of")
public class ProposalAnimalData {

    @NonNull ProposalId proposalId;
    @NonNull Name name;
    @NonNull Age age;
    @NonNull Species species;
    @NonNull Gender gender;

}
