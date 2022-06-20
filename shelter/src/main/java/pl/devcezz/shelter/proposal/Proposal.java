package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static pl.devcezz.shelter.proposal.exception.ProposalIllegalStateException.exceptionCannotAccept;
import static pl.devcezz.shelter.proposal.exception.ProposalIllegalStateException.exceptionCannotDecline;
import static pl.devcezz.shelter.proposal.exception.ProposalIllegalStateException.exceptionCannotDelete;

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
    @AttributeOverride(name = "value", column = @Column(name = "subject_id"))
    private SubjectId subjectId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Version
    private Long version;

    @ElementCollection
    @CollectionTable(name = "proposal_archive")
    private final List<ProposalArchive> archives = new ArrayList<>();

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant modificationTimestamp;

    private Proposal(SubjectId subjectId, Status status) {
        this.subjectId = subjectId;
        this.status = status;
    }

    static Proposal newOne(SubjectId subjectId) {
        return new Proposal(subjectId, Status.PENDING);
    }

    void accept() {
        archives.add(new ProposalArchive(status.name()));
        if (this.status != Status.PENDING) {
            throw exceptionCannotAccept(subjectId.getValue());
        }
        this.status = Status.ACCEPTED;
    }

    void decline() {
        archives.add(new ProposalArchive(status.name()));
        if (this.status != Status.PENDING) {
            throw exceptionCannotDecline(subjectId.getValue());
        }
        this.status = Status.DECLINED;
    }

    void delete() {
        archives.add(new ProposalArchive(status.name()));
        if (this.status != Status.PENDING) {
            throw exceptionCannotDelete(subjectId.getValue());
        }
        this.status = Status.DELETED;
    }

    SubjectId getSubjectId() {
        return subjectId;
    }

    @Embeddable
    @Access(AccessType.FIELD)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class ProposalArchive {

        private String status;

        private final Instant changeTimestamp = Instant.now();

        private ProposalArchive(String status) {
            this.status = status;
        }
    }
}
