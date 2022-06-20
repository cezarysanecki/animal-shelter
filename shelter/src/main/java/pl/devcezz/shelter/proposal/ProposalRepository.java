package pl.devcezz.shelter.proposal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

interface ProposalRepository extends JpaRepository<Proposal, UUID> {

    @Query("UPDATE Proposal ap SET ap.status = 'DELETED' WHERE ap.subjectId = :subjectId")
    @Modifying
    void declineProposalFor(@Param("subjectId") SubjectId subjectId);

    Optional<Proposal> findFirstBySubjectIdOrderByCreationTimestampDesc(SubjectId subjectId);

    default Optional<Proposal> findLatestProposalFor(SubjectId subjectId) {
        return findFirstBySubjectIdOrderByCreationTimestampDesc(subjectId);
    }
}
