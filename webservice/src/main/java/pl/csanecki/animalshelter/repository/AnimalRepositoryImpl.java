package pl.csanecki.animalshelter.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.dto.AnimalCreated;
import pl.csanecki.animalshelter.dto.AnimalRequest;
import pl.csanecki.animalshelter.service.AnimalRepository;

public class AnimalRepositoryImpl implements AnimalRepository {

    private final JdbcTemplate jdbcTemplate;

    public AnimalRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AnimalCreated save(AnimalRequest animal) {
        throw new UnsupportedOperationException();
    }
}
