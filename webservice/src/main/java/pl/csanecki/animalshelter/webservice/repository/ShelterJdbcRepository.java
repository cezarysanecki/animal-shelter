package pl.csanecki.animalshelter.webservice.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.domain.service.ShelterRepository;

public class ShelterJdbcRepository implements ShelterRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShelterJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
