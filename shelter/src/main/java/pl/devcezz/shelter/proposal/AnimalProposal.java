package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.devcezz.shelter.proposal.exception.AnimalProposalIllegalStateException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static pl.devcezz.shelter.proposal.exception.AnimalProposalIllegalStateException.*;

@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class AnimalProposal {

    private enum Status {
        PENDING, DECLINED, ACCEPTED, DELETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "animalProposalId"))
    private AnimalProposalId animalProposalId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private AnimalProposal(AnimalProposalId animalProposalId, Status status) {
        this.animalProposalId = animalProposalId;
        this.status = status;
    }

    static AnimalProposal newOne(AnimalProposalId animalProposalId) {
        return new AnimalProposal(animalProposalId, Status.PENDING);
    }

    void accept() {
        if (this.status != Status.PENDING) {
            throw exceptionCannotAccept();
        }
        this.status = Status.ACCEPTED;
    }

    void decline() {
        if (this.status != Status.PENDING) {
            throw exceptionCannotDecline();
        }
        this.status = Status.DECLINED;
    }
}
