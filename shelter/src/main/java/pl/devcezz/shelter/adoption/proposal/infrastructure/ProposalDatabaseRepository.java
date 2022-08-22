package pl.devcezz.shelter.adoption.proposal.infrastructure;

import io.vavr.API;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.shelter.adoption.proposal.model.AcceptedProposal;
import pl.devcezz.shelter.adoption.proposal.model.DeletedProposal;
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal;
import pl.devcezz.shelter.adoption.proposal.model.Proposal;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;
import pl.devcezz.shelter.commons.aggregates.AggregateRootIsStale;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.control.Option.none;
import static io.vavr.control.Option.of;
import static java.time.Instant.now;
import static pl.devcezz.shelter.adoption.proposal.infrastructure.ProposalEntity.ProposalState;
import static pl.devcezz.shelter.adoption.proposal.infrastructure.ProposalEntity.ProposalState.Accepted;
import static pl.devcezz.shelter.adoption.proposal.infrastructure.ProposalEntity.ProposalState.Deleted;
import static pl.devcezz.shelter.adoption.proposal.infrastructure.ProposalEntity.ProposalState.Pending;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ProposalDatabaseRepository implements Proposals {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Option<Proposal> findBy(ProposalId proposalId) {
        return findByProposalId(proposalId)
                .map(ProposalEntity::toDomainModel);
    }

    private Option<ProposalEntity> findByProposalId(ProposalId proposalId) {
        return Try
                .ofSupplier(() -> of(
                        jdbcTemplate.queryForObject(
                                "SELECT p.* FROM proposal p WHERE p.proposal_id = ?",
                                new BeanPropertyRowMapper<>(ProposalEntity.class),
                                proposalId.getValue().toString())))
                .getOrElse(none());
    }

    @Override
    public void save(Proposal proposal) {
        findByProposalId(proposal.getProposalId())
                .map(this::insertArchive)
                .map(entity -> updateOptimistically(proposal))
                .onEmpty(() -> insertNew(proposal));
    }

    private ProposalEntity insertArchive(ProposalEntity entity) {
        jdbcTemplate.update("" +
                        "INSERT INTO proposal_archive " +
                        "(proposal_id, proposal_state, change_timestamp) VALUES " +
                        "(?, ?, ?)",
                entity.getId(), entity.getProposal_state().name(), now());
        return entity;
    }

    private int updateOptimistically(Proposal proposal) {
        int result = Match(proposal).of(
                API.Case(API.$(instanceOf(PendingProposal.class)), this::update),
                API.Case(API.$(instanceOf(AcceptedProposal.class)), this::update),
                API.Case(API.$(instanceOf(DeletedProposal.class)), this::update)
        );
        if (result == 0) {
            throw new AggregateRootIsStale("in the meantime proposal has been updated: " + proposal.getProposalId());
        }
        return result;
    }

    private int update(PendingProposal pendingProposal) {
        return jdbcTemplate.update("" +
                        "UPDATE proposal p SET " +
                        "p.proposal_state = ?, p.modification_timestamp = ?, p.version = ? " +
                        "WHERE p.proposal_id = ? AND p.version = ?",
                Pending.name(),
                now(),
                pendingProposal.getVersion().getVersion() + 1,
                pendingProposal.getProposalId().getValue().toString(),
                pendingProposal.getVersion().getVersion());
    }

    private int update(AcceptedProposal acceptedProposal) {
        return jdbcTemplate.update("" +
                        "UPDATE proposal p SET " +
                        "p.proposal_state = ?, p.modification_timestamp = ?, p.version = ? " +
                        "WHERE p.proposal_id = ? AND p.version = ?",
                Accepted.name(),
                now(),
                acceptedProposal.getVersion().getVersion() + 1,
                acceptedProposal.getProposalId().getValue().toString(),
                acceptedProposal.getVersion().getVersion());
    }

    private int update(DeletedProposal deletedProposal) {
        return jdbcTemplate.update("" +
                        "UPDATE proposal p SET " +
                        "p.proposal_state = ?, p.modification_timestamp = ?, p.version = ? " +
                        "WHERE p.proposal_id = ? AND p.version = ?",
                Deleted.name(),
                now(),
                deletedProposal.getVersion().getVersion() + 1,
                deletedProposal.getProposalId().getValue().toString(),
                deletedProposal.getVersion().getVersion());
    }

    private void insertNew(Proposal proposal) {
        Match(proposal).of(
                Case($(instanceOf(PendingProposal.class)), this::insert),
                Case($(instanceOf(AcceptedProposal.class)), this::insert),
                Case($(instanceOf(DeletedProposal.class)), this::insert)
        );
    }

    private int insert(PendingProposal pendingProposal) {
        return insert(pendingProposal.getProposalId(), Pending);
    }

    private int insert(AcceptedProposal acceptedProposal) {
        return insert(acceptedProposal.getProposalId(), Accepted);
    }

    private int insert(DeletedProposal deletedProposal) {
        return insert(deletedProposal.getProposalId(), Deleted);
    }

    private int insert(ProposalId proposalId, ProposalState state) {
        return jdbcTemplate.update("" +
                        "INSERT INTO proposal " +
                        "(proposal_id, proposal_state, creation_timestamp, modification_timestamp, version) VALUES " +
                        "(?, ?, ?, ?, 0)",
                proposalId.getValue().toString(), state.name(), now(), now());
    }
}
