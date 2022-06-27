package pl.devcezz.shelter.adoption.shelter.infrastructure;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "proposalId")
class AcceptedProposalDatabaseEntity {

    @Id
    private Long id;
    private String proposalId;

    AcceptedProposalDatabaseEntity(UUID proposalId) {
        this.proposalId = proposalId.toString();
    }

    Long getId() {
        return id;
    }

    UUID getProposalId() {
        return UUID.fromString(proposalId);
    }
}
