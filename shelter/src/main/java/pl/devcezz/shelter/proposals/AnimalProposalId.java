package pl.devcezz.shelter.proposals;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
class AnimalProposalId {

    private String value;

    private AnimalProposalId(UUID value) {
        this.value = value.toString();
    }

    static AnimalProposalId of(UUID value) {
        return new AnimalProposalId(value);
    }

    UUID getValue() {
        return UUID.fromString(value);
    }
}
