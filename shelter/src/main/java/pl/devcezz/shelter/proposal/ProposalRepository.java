package pl.devcezz.shelter.proposal;

import org.springframework.data.repository.CrudRepository;
import pl.devcezz.shelter.proposal.exception.ProposalNotFoundException;

import java.util.Optional;
import java.util.UUID;

interface ProposalRepository extends CrudRepository<Proposal, UUID> {

    Optional<Proposal> findFirstBySubjectIdOrderByCreationTimestampDesc(SubjectId subjectId);

    default Proposal findLatestProposalFor(SubjectId subjectId) {
        return findFirstBySubjectIdOrderByCreationTimestampDesc(subjectId)
                .orElseThrow(() -> new ProposalNotFoundException(subjectId.getValue()));
    }
}
