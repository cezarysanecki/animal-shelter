package pl.devcezz.shelter.proposal.infrastructure;

import io.vavr.API;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.devcezz.shelter.proposal.AcceptedProposal;
import pl.devcezz.shelter.proposal.DeclinedProposal;
import pl.devcezz.shelter.proposal.DeletedProposal;
import pl.devcezz.shelter.proposal.PendingProposal;
import pl.devcezz.shelter.proposal.ProposalI;

import java.util.UUID;

import static io.vavr.API.*;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
class ProposalEntity {

    enum ProposalState {
        Pending, Accepted, Declined, Deleted
    }

    UUID subject_id;
    int version;
    ProposalState proposal_state;

    ProposalI toDomainModel() {
        return Match(proposal_state).of(
                        Case($(ProposalState.Pending), this::toPendingProposal),
                        Case($(ProposalState.Accepted), this::toAcceptedProposal),
                        Case($(ProposalState.Declined), this::toDeclinedProposal),
                        Case($(ProposalState.Deleted), this::toDeletedProposal)
                );
    }

    private PendingProposal toPendingProposal() {
        return new PendingProposal(subject_id, version);
    }

    private AcceptedProposal toAcceptedProposal() {
        return new AcceptedProposal(subject_id, version);
    }

    private DeclinedProposal toDeclinedProposal() {
        return new DeclinedProposal(subject_id, version);
    }

    private DeletedProposal toDeletedProposal() {
        return new DeletedProposal(subject_id, version);
    }
}
