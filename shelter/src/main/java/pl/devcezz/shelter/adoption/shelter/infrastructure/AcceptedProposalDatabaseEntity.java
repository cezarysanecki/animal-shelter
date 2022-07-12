package pl.devcezz.shelter.adoption.shelter.infrastructure;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

import static pl.devcezz.shelter.adoption.shelter.infrastructure.AcceptedProposalDatabaseEntity.Status.Confirmed;
import static pl.devcezz.shelter.adoption.shelter.infrastructure.AcceptedProposalDatabaseEntity.Status.Pending;

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

    private AcceptedProposalDatabaseEntity(UUID proposalId, Status status) {
        this.proposalId = proposalId.toString();
    }

    static AcceptedProposalDatabaseEntity ofPending(UUID proposalId) {
        return new AcceptedProposalDatabaseEntity(proposalId, Pending);
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

    boolean isPending() {
        return status == Pending;
    }

    boolean isConfirmed() {
        return status == Pending;
    }
}
