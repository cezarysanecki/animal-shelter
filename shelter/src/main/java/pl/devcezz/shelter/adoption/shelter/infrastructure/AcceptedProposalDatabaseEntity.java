package pl.devcezz.shelter.adoption.shelter.infrastructure;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
class AcceptedProposalDatabaseEntity {

    @Id
    Long id;
    UUID proposalId;

    AcceptedProposalDatabaseEntity(UUID proposalId) {
        this.proposalId = proposalId;
    }
}
