package pl.csanecki.animalshelter.webservice.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.domain.service.AnimalRepository;

public class AnimalRepositoryImpl implements AnimalRepository {

    private final JdbcTemplate jdbcTemplate;

    public AnimalRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
