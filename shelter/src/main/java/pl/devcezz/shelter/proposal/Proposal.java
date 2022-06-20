package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
import java.time.Instant;

import static pl.devcezz.shelter.proposal.exception.ProposalIllegalStateException.exceptionCannotAccept;
import static pl.devcezz.shelter.proposal.exception.ProposalIllegalStateException.exceptionCannotDecline;

@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Proposal {

    private enum Status {
        PENDING, DECLINED, ACCEPTED, DELETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "subjectId"))
    private SubjectId subjectId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private Instant creationTimestamp;

    private Proposal(SubjectId subjectId, Status status) {
        this.subjectId = subjectId;
        this.status = status;
    }

    static Proposal newOne(SubjectId subjectId) {
        return new Proposal(subjectId, Status.PENDING);
    }

    Proposal accept() {
        if (this.status != Status.PENDING) {
            throw exceptionCannotAccept(subjectId.getValue());
        }
        return new Proposal(subjectId, Status.ACCEPTED);
    }

    Proposal decline() {
        if (this.status != Status.PENDING) {
            throw exceptionCannotDecline(subjectId.getValue());
        }
        return new Proposal(subjectId, Status.DECLINED);
    }

    SubjectId getSubjectId() {
        return subjectId;
    }
}
