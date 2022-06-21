package pl.devcezz.shelter.proposal.infrastructure;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.shelter.proposal.AcceptedProposal;
import pl.devcezz.shelter.proposal.DeclinedProposal;
import pl.devcezz.shelter.proposal.DeletedProposal;
import pl.devcezz.shelter.proposal.PendingProposal;
import pl.devcezz.shelter.proposal.Proposal;
import pl.devcezz.shelter.proposal.ProposalId;
import pl.devcezz.shelter.proposal.ProposalRepository;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.control.Option.none;
import static io.vavr.control.Option.of;
import static java.time.Instant.now;
import static pl.devcezz.shelter.proposal.infrastructure.ProposalEntity.ProposalState.Accepted;
import static pl.devcezz.shelter.proposal.infrastructure.ProposalEntity.ProposalState.Declined;
import static pl.devcezz.shelter.proposal.infrastructure.ProposalEntity.ProposalState.Deleted;
import static pl.devcezz.shelter.proposal.infrastructure.ProposalEntity.ProposalState.Pending;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ProposalDatabaseRepository implements ProposalRepository {

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
                                proposalId.getValue())))
                .getOrElse(none());
    }

    @Override
    public void save(Proposal proposal) {
        findByProposalId(proposal.getProposalId())
                .map(this::insertArchive)
                .map(entity -> updateOptimistically(proposal))
                .onEmpty(() -> insertNew(proposal));
    }

    private int insertArchive(ProposalEntity entity) {
        return jdbcTemplate.update("INSERT INTO proposal_archive " +
                        "(proposal_id, " +
                        "status, " +
                        "change_timestamp) VALUES " +
                        "(?, ?, ?)",
                entity.getProposal_id(), entity.getProposal_state().toString(), now());
    }

    private int updateOptimistically(Proposal proposal) {
        int result = Match(proposal).of(
                Case($(instanceOf(PendingProposal.class)), this::update),
                Case($(instanceOf(AcceptedProposal.class)), this::update),
                Case($(instanceOf(DeclinedProposal.class)), this::update),
                Case($(instanceOf(DeletedProposal.class)), this::update)
        );
        if (result == 0) {
            throw new IllegalStateException("in the meantime proposal has been updated: " + proposal.getProposalId());
        }
        return result;
    }

    private int update(PendingProposal pendingProposal) {
        return jdbcTemplate.update("UPDATE proposal p SET " +
                        "p.status = ?, p.modification_timestamp = ?, p.version = ? " +
                        "WHERE p.proposal_id = ?",
                Pending,
                now(),
                pendingProposal.getVersion().getVersion() + 1,
                pendingProposal.getProposalId().getValue());
    }

    private int update(AcceptedProposal acceptedProposal) {
        return jdbcTemplate.update("UPDATE proposal p SET " +
                        "p.status = ?, p.modification_timestamp = ?, p.version = ? " +
                        "WHERE p.proposal_id = ?",
                Accepted,
                now(),
                acceptedProposal.getVersion().getVersion() + 1,
                acceptedProposal.getProposalId().getValue());
    }

    private int update(DeclinedProposal declinedProposal) {
        return jdbcTemplate.update("UPDATE proposal p SET " +
                        "p.status = ?, p.modification_timestamp = ?, p.version = ? " +
                        "WHERE p.proposal_id = ?",
                Declined,
                now(),
                declinedProposal.getVersion().getVersion() + 1,
                declinedProposal.getProposalId().getValue());
    }

    private int update(DeletedProposal deletedProposal) {
        return jdbcTemplate.update("UPDATE proposal p SET " +
                        "p.status = ?, p.modification_timestamp = ?, p.version = ? " +
                        "WHERE p.proposal_id = ?",
                Deleted,
                now(),
                deletedProposal.getVersion().getVersion() + 1,
                deletedProposal.getProposalId().getValue());
    }

    private void insertNew(Proposal proposal) {
        Match(proposal).of(
                Case($(instanceOf(PendingProposal.class)), this::insert),
                Case($(instanceOf(AcceptedProposal.class)), this::insert),
                Case($(instanceOf(DeclinedProposal.class)), this::insert),
                Case($(instanceOf(DeletedProposal.class)), this::insert)
        );
    }

    private int insert(PendingProposal pendingProposal) {
        return insert(pendingProposal.getProposalId(), Pending);
    }

    private int insert(AcceptedProposal acceptedProposal) {
        return insert(acceptedProposal.getProposalId(), Accepted);
    }

    private int insert(DeclinedProposal declinedProposal) {
        return insert(declinedProposal.getProposalId(), Declined);
    }

    private int insert(DeletedProposal deletedProposal) {
        return insert(deletedProposal.getProposalId(), Deleted);
    }

    private int insert(ProposalId proposalId, ProposalEntity.ProposalState state) {
        return jdbcTemplate.update("INSERT INTO proposal " +
                        "(proposal_id, " +
                        "status, " +
                        "creation_timestamp, " +
                        "modification_timestamp, " +
                        "version VALUES " +
                        "(?, ?, ?, ?, 0)",
                proposalId.getValue(), state.toString(), now(), now());
    }
}
