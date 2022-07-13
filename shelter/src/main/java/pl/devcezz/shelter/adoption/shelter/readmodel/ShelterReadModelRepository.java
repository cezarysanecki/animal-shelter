package pl.devcezz.shelter.adoption.shelter.readmodel;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import pl.devcezz.shelter.adoption.shelter.readmodel.dto.ShelterProposalDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ShelterReadModelRepository {

    private final JdbcTemplate jdbcTemplate;

    List<ShelterProposalDto> findAcceptedProposals() {
        return jdbcTemplate.query("" +
                        "SELECT a.proposal_id, a.status " +
                        "FROM accepted_proposal_database_entity a",
                new ShelterProposalRowMapper());
    }
}

class ShelterProposalRowMapper implements RowMapper<ShelterProposalDto> {

    @Override
    public ShelterProposalDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ShelterProposalDto.builder()
                .proposalId(UUID.fromString(rs.getString("proposal_id")))
                .status(rs.getString("status"))
                .build();
    }
}
