package pl.devcezz.shelter.adoption.shelter.readmodel;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ShelterReadModelRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<ShelterProposalDto> findAcceptedProposals() {
        return jdbcTemplate.queryForList("" +
                        "SELECT a.proposal_id, a.status " +
                        "FROM accepted_proposal_database_entity a",
                ShelterProposalDto.class);
    }
}
