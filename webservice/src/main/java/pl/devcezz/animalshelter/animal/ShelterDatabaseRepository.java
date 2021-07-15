package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.cqrs.event.EventsBus;

import java.util.UUID;

class ShelterDatabaseRepository implements ShelterRepository, Animals {

    private final JdbcTemplate jdbcTemplate;
    private final EventsBus eventsBus;

    ShelterDatabaseRepository(final JdbcTemplate jdbcTemplate, final EventsBus eventsBus) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventsBus = eventsBus;
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
    public void publish(final AnimalEvent event) {
        eventsBus.publish(event);
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