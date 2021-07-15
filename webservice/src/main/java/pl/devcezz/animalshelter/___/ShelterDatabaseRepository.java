package pl.devcezz.animalshelter.___;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.animalshelter.___.animal.Animal;
import pl.devcezz.animalshelter.___.animal.vo.AnimalId;
import pl.devcezz.animalshelter.___.animal.vo.ShelterAnimal;
import pl.devcezz.animalshelter.___.animal.ShelterRepository;
import pl.devcezz.animalshelter.___.animal.vo.ShelterLimits;

import java.util.UUID;

class ShelterDatabaseRepository implements ShelterRepository {

    private final JdbcTemplate jdbcTemplate;

    ShelterDatabaseRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void save(Animal animal) {
        jdbcTemplate.update("" +
                        "INSERT INTO shelter_animal " +
                        "(name, species, age) VALUES " +
                        "(?, ?, ?)",
                animal.getName().value(), animal.getSpecies(), animal.getAge().value());
    }

    @Override
    public ShelterLimits queryForShelterLimits() {
        return Option.of(
                jdbcTemplate.queryForObject(
                    "SELECT c.capacity, c.safe_threshold FROM shelter_config c",
                    new BeanPropertyRowMapper<>(ShelterLimitsRow.class)
                ))
                .map(ShelterLimitsRow::toShelterLimits)
                .getOrElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Set<ShelterAnimal> queryForAnimalsInShelter() {
        return Stream.ofAll(
                jdbcTemplate.query(
                    "SELECT a.id, a.name, a.species, a.age FROM shelter_animal a WHERE a.adopted_at = NULL",
                    new BeanPropertyRowMapper<>(ShelterAnimalRow.class)
                ))
                .map(ShelterAnimalRow::toShelterAnimal)
                .toSet();
    }
}

record ShelterLimitsRow(int capacity, int safeThreshold) {

    ShelterLimits toShelterLimits() {
        return new ShelterLimits(capacity, safeThreshold);
    }
}

record ShelterAnimalRow(UUID id) {

    ShelterAnimal toShelterAnimal() {
        return new ShelterAnimal(new AnimalId(id));
    }
}