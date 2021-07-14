package pl.csanecki.animalshelter.___;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.___.animal.Animal;
import pl.csanecki.animalshelter.___.animal.ShelterAnimal;
import pl.csanecki.animalshelter.___.animal.ShelterRepository;
import pl.csanecki.animalshelter.___.animal.vo.ShelterLimits;
import pl.csanecki.animalshelter.___.species.Species;
import pl.csanecki.animalshelter.___.species.SpeciesRepository;

import java.util.UUID;

class ShelterDatabaseRepository implements ShelterRepository, SpeciesRepository {

    private final JdbcTemplate jdbcTemplate;

    ShelterDatabaseRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<Species> findAllSpecies() {
        return Stream.ofAll(
                jdbcTemplate.query(
                        "SELECT s.* FROM shelter_species",
                        new BeanPropertyRowMapper<>(SpeciesRow.class)))
                .map(SpeciesRow::toSpecies)
                .toSet();
    }

    @Override
    public void save(final Species species) {
        jdbcTemplate.update("" +
                        "INSERT INTO shelter_species " +
                        "(value) VALUES " +
                        "(?)",
                species.getValue());
    }


    @Override
    public void save(Animal animal) {
        jdbcTemplate.update("" +
                        "INSERT INTO shelter_animal " +
                        "(name, species, age) VALUES " +
                        "(?, ?, ?)",
                animal.getName().getValue(), animal.getSpecies().getValue(), animal.getAge().getValue());
    }

    @Override
    public ShelterLimits queryForShelterLimits() {
        return Option.of(
                jdbcTemplate.queryForObject(
                    "SELECT c.capacity, c.safeThreshold FROM shelter_config c",
                    new BeanPropertyRowMapper<>(ShelterLimitsRow.class)
                ))
                .map(ShelterLimitsRow::toShelterLimits)
                .getOrElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Set<ShelterAnimal> queryForAnimalsInShelter() {
        return Stream.ofAll(
                jdbcTemplate.query(
                    "SELECT a.id, a.name, a.species, a.age FROM shelter_animal a WHERE a.adoptedAt = NULL",
                    new BeanPropertyRowMapper<>(ShelterAnimalRow.class)
                ))
                .map(ShelterAnimalRow::toShelterAnimal)
                .toSet();
    }
}

class SpeciesRow {

    private final String value;

    SpeciesRow(final String value) {
        this.value = value;
    }

    Species toSpecies() {
        return new Species(value);
    }
}

class ShelterLimitsRow {

    private final int capacity;
    private final int safeThreshold;

    ShelterLimitsRow(final int capacity, final int safeThreshold) {
        this.capacity = capacity;
        this.safeThreshold = safeThreshold;
    }

    ShelterLimits toShelterLimits() {
        return new ShelterLimits(capacity, safeThreshold);
    }
}

class ShelterAnimalRow {

    private final UUID id;

    ShelterAnimalRow(final UUID id) {
        this.id = id;
    }

    ShelterAnimal toShelterAnimal() {
        return new ShelterAnimal(id);
    }
}