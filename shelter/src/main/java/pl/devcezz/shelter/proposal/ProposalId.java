package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@EqualsAndHashCode
public class ProposalId {

    private UUID value;

    private ProposalId(UUID value) {
        this.value = value;
    }

    public static ProposalId of(UUID value) {
        return new ProposalId(value);
    }

    public UUID getValue() {
        return value;
    }
}
