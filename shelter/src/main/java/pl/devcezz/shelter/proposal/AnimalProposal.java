package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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

@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class AnimalProposal {

    enum Status {
        PENDING, DECLINED, ACCEPTED
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
        this.status = Status.ACCEPTED;
    }

    void decline() {
        this.status = Status.DECLINED;
    }
}
