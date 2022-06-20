package pl.devcezz.shelter.proposal;

import org.springframework.data.repository.CrudRepository;
import pl.devcezz.shelter.proposal.exception.ProposalNotFoundException;

import java.util.Optional;

interface ProposalRepository extends CrudRepository<Proposal, Long> {

    Optional<Proposal> findProposalBy(SubjectId subjectId);

    default Proposal findProposalFor(SubjectId subjectId) {
        return findProposalBy(subjectId)
                .orElseThrow(() -> new ProposalNotFoundException(subjectId.getValue()));
    }
}
