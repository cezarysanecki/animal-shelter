package pl.csanecki.animalshelter.webservice.repository;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.service.ShelterRepository;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.of;

public class ShelterJdbcRepository implements ShelterRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShelterJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Option<AnimalDetails> getAnimalDetails(long animalId) {
        return Try.ofSupplier(() -> of(
                jdbcTemplate.queryForObject("SELECT * FROM animals WHERE id = ?",
                new BeanPropertyRowMapper<>(AnimalEntity.class),
                animalId))
        ).getOrElse(none())
                .map(AnimalEntity::toAnimalDetails);
    }

    @Override
    public List<AnimalShortInfo> getAnimalsInfo() {
        return List.ofAll(jdbcTemplate.query("SELECT * FROM animals", new BeanPropertyRowMapper<>(AnimalEntity.class)))
                .map(AnimalEntity::toAnimalShortInfo);
    }
}