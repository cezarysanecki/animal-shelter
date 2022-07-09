package pl.devcezz.shelter.adoption.proposal.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ProposalId {

    private UUID value;

    public static ProposalId of(UUID value) {
        return new ProposalId(value);
    }

    public UUID getValue() {
        return value;
    }
}
