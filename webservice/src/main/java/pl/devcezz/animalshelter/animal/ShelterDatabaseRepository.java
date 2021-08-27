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
                        "(animal_id, name, species, age) VALUES " +
                        "(?, ?, ?, ?)",
                animal.getId().value().toString(), animal.getName().value(), animal.getSpecies().name(), animal.getAge().value());
    }

    @Override
    public Option<ShelterAnimal> findNotAdoptedBy(final AnimalId animalId) {
        return Option.of(
                jdbcTemplate.queryForObject(
                        "SELECT a.animal_id FROM shelter_animal a WHERE a.animal_id = ? AND a.adopted_at IS NULL",
                        new BeanPropertyRowMapper<>(ShelterAnimalRow.class),
                        animalId.value().toString()
                ))
                .map(ShelterAnimalRow::toShelterAnimal);
    }

    @Override
    public void adopt(final ShelterAnimal animal) {
        jdbcTemplate.update("" +
                        "UPDATE shelter_animal a" +
                        "SET a.adopted_at = NOW()" +
                        "WHERE a.animal_id = ?",
                animal.animalId().value().toString());
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
                    "SELECT a.animal_id FROM shelter_animal a WHERE a.adopted_at IS NULL",
                    new BeanPropertyRowMapper<>(ShelterAnimalRow.class)
                ))
                .map(ShelterAnimalRow::toShelterAnimal)
                .toSet();
    }
}

class ShelterLimitsRow {

    int capacity;
    int safeThreshold;

    ShelterLimits toShelterLimits() {
        return new ShelterLimits(capacity, safeThreshold);
    }

    public void setCapacity(final int capacity) {
        this.capacity = capacity;
    }

    public void setSafeThreshold(final int safeThreshold) {
        this.safeThreshold = safeThreshold;
    }
}

class ShelterAnimalRow {

    UUID animalId;

    ShelterAnimal toShelterAnimal() {
        return new ShelterAnimal(new AnimalId(animalId));
    }

    public void setAnimalId(final UUID animalId) {
        this.animalId = animalId;
    }
}