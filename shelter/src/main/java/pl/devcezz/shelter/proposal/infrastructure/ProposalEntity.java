package pl.devcezz.shelter.proposal.infrastructure;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.devcezz.shelter.proposal.AcceptedProposal;
import pl.devcezz.shelter.proposal.DeclinedProposal;
import pl.devcezz.shelter.proposal.DeletedProposal;
import pl.devcezz.shelter.proposal.PendingProposal;
import pl.devcezz.shelter.proposal.Proposal;
import pl.devcezz.shelter.proposal.SubjectId;
import pl.devcezz.shelter.shared.Version;

import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
class ProposalEntity {

    enum ProposalState {
        Pending, Accepted, Declined, Deleted
    }

    UUID subject_id;
    int version;
    ProposalState proposal_state;

    Proposal toDomainModel() {
        return Match(proposal_state).of(
                Case($(ProposalState.Pending), this::toPendingProposal),
                Case($(ProposalState.Accepted), this::toAcceptedProposal),
                Case($(ProposalState.Declined), this::toDeclinedProposal),
                Case($(ProposalState.Deleted), this::toDeletedProposal)
        );
    }

    private PendingProposal toPendingProposal() {
        return new PendingProposal(SubjectId.of(subject_id), new Version(version));
    }

    private AcceptedProposal toAcceptedProposal() {
        return new AcceptedProposal(SubjectId.of(subject_id), new Version(version));
    }

    private DeclinedProposal toDeclinedProposal() {
        return new DeclinedProposal(SubjectId.of(subject_id), new Version(version));
    }

    private DeletedProposal toDeletedProposal() {
        return new DeletedProposal(SubjectId.of(subject_id), new Version(version));
    }
}
