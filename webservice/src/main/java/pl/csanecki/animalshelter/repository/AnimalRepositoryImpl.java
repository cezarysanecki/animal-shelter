package pl.csanecki.animalshelter.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.service.AnimalRepository;

public class AnimalRepositoryImpl implements AnimalRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AnimalRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
