package pl.devcezz.shelter.adoption.shelter.infrastructure;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
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
