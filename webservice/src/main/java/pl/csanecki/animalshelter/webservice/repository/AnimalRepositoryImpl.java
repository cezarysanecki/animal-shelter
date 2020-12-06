package pl.csanecki.animalshelter.webservice.repository;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import pl.csanecki.animalshelter.domain.animal.model.AnimalId;
import pl.csanecki.animalshelter.domain.service.AnimalRepository;
import pl.csanecki.animalshelter.domain.service.entity.AnimalData;
import pl.csanecki.animalshelter.domain.service.entity.AnimalInformation;

import java.sql.PreparedStatement;
import java.sql.Statement;

import static io.vavr.control.Option.none;

public class AnimalRepositoryImpl implements AnimalRepository {

    private final JdbcTemplate jdbcTemplate;

    public AnimalRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Option<AnimalData> save(AnimalInformation animalInformation) {
        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO animals(name, kind, age) VALUES(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, animalInformation.name.getName());
            preparedStatement.setString(2, animalInformation.kind.getKind().name());
            preparedStatement.setInt(3, animalInformation.age.getAge());
            return preparedStatement;
        }, holder);

        return Option.of(holder.getKey())
                    .map(key -> findAnimalBy(new AnimalId(key.intValue())))
                    .getOrElse(none());
    }

    @Override
    public Option<AnimalData> findAnimalBy(AnimalId animalId) {
        try {
            AnimalEntity animalEntity = jdbcTemplate.queryForObject(
                    "SELECT * FROM animals WHERE id = ?",
                    new BeanPropertyRowMapper<>(AnimalEntity.class),
                    animalId.getAnimalId()
            );
            return Option.of(animalEntity)
                    .map(AnimalEntity::toAnimalData)
                    .orElse(none());
        } catch (EmptyResultDataAccessException ex) {
            return none();
        }
    }

    @Override
    public List<AnimalData> findAll() {
        return List.ofAll(jdbcTemplate.query(
                "SELECT * FROM animals",
                new BeanPropertyRowMapper<>(AnimalEntity.class)
        )).map(AnimalEntity::toAnimalData);
    }
}
