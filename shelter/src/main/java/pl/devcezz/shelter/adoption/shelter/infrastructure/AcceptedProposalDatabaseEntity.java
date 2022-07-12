package pl.devcezz.shelter.adoption.shelter.infrastructure;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

import static pl.devcezz.shelter.adoption.shelter.infrastructure.AcceptedProposalDatabaseEntity.Status.Confirmed;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "proposalId")
class AcceptedProposalDatabaseEntity {

    enum Status {
        Pending, Confirmed
    }

    @Id
    private Long id;
    private String proposalId;
    private Status status;

    AcceptedProposalDatabaseEntity(UUID proposalId) {
        this.proposalId = proposalId.toString();
    }

    Long getId() {
        return id;
    }

    UUID getProposalId() {
        return UUID.fromString(proposalId);
    }

    void confirm() {
        this.status = Confirmed;
    }
}
