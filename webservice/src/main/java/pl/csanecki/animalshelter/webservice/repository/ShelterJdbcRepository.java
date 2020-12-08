package pl.csanecki.animalshelter.webservice.repository;

import io.vavr.control.Option;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import pl.csanecki.animalshelter.domain.command.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.model.AnimalId;
import pl.csanecki.animalshelter.domain.service.ShelterRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class ShelterJdbcRepository implements ShelterRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShelterJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public AnimalId registerAnimal(AddAnimalCommand command) {
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO animals(name, kind, age) VALUES(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, command.getAnimalName());
            preparedStatement.setString(2, command.getAnimalKind());
            preparedStatement.setInt(3, command.getAnimalAge());
            return preparedStatement;
        }, holder);

        return Option.of(holder.getKey())
                .map(key -> AnimalId.of(key.longValue()))
                .getOrElseThrow(() -> { throw new DatabaseRuntimeError("Cannot get id for admitted animal"); });
    }
}
