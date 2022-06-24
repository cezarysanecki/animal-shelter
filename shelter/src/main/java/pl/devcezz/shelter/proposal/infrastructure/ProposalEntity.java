package pl.devcezz.shelter.proposal.infrastructure;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.devcezz.shelter.proposal.model.AcceptedProposal;
import pl.devcezz.shelter.proposal.model.DeletedProposal;
import pl.devcezz.shelter.proposal.model.PendingProposal;
import pl.devcezz.shelter.proposal.model.Proposal;
import pl.devcezz.shelter.proposal.model.ProposalId;
import pl.devcezz.shelter.shared.Version;

import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static pl.devcezz.shelter.proposal.infrastructure.ProposalEntity.ProposalState.Accepted;
import static pl.devcezz.shelter.proposal.infrastructure.ProposalEntity.ProposalState.Deleted;
import static pl.devcezz.shelter.proposal.infrastructure.ProposalEntity.ProposalState.Pending;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
class ProposalEntity {

    enum ProposalState {
        Pending, Accepted, Deleted
    }

    UUID proposal_id;
    UUID subject_id;
    int version;
    ProposalState proposal_state;

    Proposal toDomainModel() {
        return Match(proposal_state).of(
                Case($(Pending), this::toPendingProposal),
                Case($(Accepted), this::toAcceptedProposal),
                Case($(Deleted), this::toDeletedProposal)
        );
    }

    private PendingProposal toPendingProposal() {
        return new PendingProposal(ProposalId.of(subject_id), new Version(version));
    }

    private AcceptedProposal toAcceptedProposal() {
        return new AcceptedProposal(ProposalId.of(subject_id), new Version(version));
    }

    private DeletedProposal toDeletedProposal() {
        return new DeletedProposal(ProposalId.of(subject_id), new Version(version));
    }
}
