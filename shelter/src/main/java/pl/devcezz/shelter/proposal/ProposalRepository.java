package pl.devcezz.shelter.proposal;

import org.springframework.data.repository.CrudRepository;
import pl.devcezz.shelter.proposal.exception.ProposalNotFoundException;

import java.util.Optional;

interface ProposalRepository extends CrudRepository<Proposal, Long> {

    Optional<Proposal> findProposalBySubjectId(SubjectId subjectId);

    default Proposal findProposalFor(SubjectId subjectId) {
        return findProposalBySubjectId(subjectId)
                .orElseThrow(() -> new ProposalNotFoundException(subjectId.getValue()));
    }
}
