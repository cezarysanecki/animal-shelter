package pl.csanecki.animalshelter.webservice.repository;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import pl.csanecki.animalshelter.domain.service.AnimalRepository;
import pl.csanecki.animalshelter.domain.service.entity.AnimalEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnimalRepositoryImpl implements AnimalRepository {

    private final JdbcTemplate jdbcTemplate;

    public AnimalRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Option<AnimalEntity> save(AnimalEntity animal) {
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO animals(name, kind, age) VALUES(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, animal.name);
            preparedStatement.setString(2, animal.kind);
            preparedStatement.setInt(3, animal.age);
            return preparedStatement;
        }, holder);

        return Option.of(holder.getKey())
                    .map(key -> findAnimalBy(key.intValue()))
                    .getOrElse(Option.none());
    }

    @Override
    public Option<AnimalEntity> findAnimalBy(int id) {
        try {
            AnimalEntity animalEntity = jdbcTemplate.queryForObject(
                    "SELECT * FROM animals WHERE id = ?",
                    new AnimalDetailsMapper(),
                    id
            );
            return Option.of(animalEntity);
        } catch (EmptyResultDataAccessException ex) {
            return Option.none();
        }
    }

    @Override
    public List<AnimalEntity> findAll() {
        return List.ofAll(jdbcTemplate.query(
                "SELECT * FROM animals",
                new AnimalDetailsMapper()
        ));
    }

    private static final class AnimalDetailsMapper implements RowMapper<AnimalEntity> {

        public AnimalEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AnimalEntity(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("kind"),
                    rs.getInt("age"),
                    rs.getString("admittedAt"),
                    rs.getString("adoptedAt")
            );
        }
    }
}
