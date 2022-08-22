package pl.devcezz.shelter.adoption.proposal.view;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.commons.model.Age;
import pl.devcezz.shelter.commons.model.Gender;
import pl.devcezz.shelter.commons.model.Name;
import pl.devcezz.shelter.commons.model.Species;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "proposalId")
@Table("proposal_animal")
class ProposalAnimalData {

    @Id
    private Long id;
    private String proposalId;
    private String name;
    private Integer age;
    private String species;
    private String gender;

    private ProposalAnimalData(String proposalId, String name, Integer age, String species, String gender) {
        this.proposalId = proposalId;
        this.name = name;
        this.age = age;
        this.species = species;
        this.gender = gender;
    }

    static ProposalAnimalData of(ProposalId proposalId, Name name, Age age, Species species, Gender gender) {
        return new ProposalAnimalData(
                proposalId.getValue().toString(),
                name.getValue(),
                age.getValue(),
                species.getValue(),
                gender.toString());
    }
}
