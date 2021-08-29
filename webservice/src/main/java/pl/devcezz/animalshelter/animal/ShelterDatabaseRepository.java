package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.cqrs.event.EventsBus;

import java.util.UUID;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.of;

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
    public Option<ShelterAnimal> findBy(final AnimalId animalId) {
        return Try.ofSupplier(() -> of(
                        jdbcTemplate.queryForObject(
                                "SELECT a.animal_id, a.adopted_at IS NULL as in_shelter FROM shelter_animal a WHERE a.animal_id = ?",
                                new BeanPropertyRowMapper<>(ShelterAnimalRow.class),
                                animalId.value().toString()))
                        .map(ShelterAnimalRow::toShelterAnimal))
                .getOrElse(none());
    }

    @Override
    public void adopt(final AvailableAnimal animal) {
        jdbcTemplate.update("" +
                        "UPDATE shelter_animal a " +
                        "SET a.adopted_at = NOW() " +
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
    public Set<AvailableAnimal> queryForAvailableAnimals() {
        return Stream.ofAll(
                jdbcTemplate.query(
                    "SELECT a.animal_id FROM shelter_animal a WHERE a.adopted_at IS NULL",
                    new BeanPropertyRowMapper<>(AvailableAnimalRow.class)
                ))
                .map(AvailableAnimalRow::toAvailableAnimal)
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

class AvailableAnimalRow {

    UUID animalId;

    AvailableAnimal toAvailableAnimal() {
        return new AvailableAnimal(new AnimalId(animalId));
    }

    public void setAnimalId(final UUID animalId) {
        this.animalId = animalId;
    }
}

class ShelterAnimalRow {

    UUID animalId;
    Boolean inShelter;

    ShelterAnimal toShelterAnimal() {
        return inShelter ? new AvailableAnimal(new AnimalId(animalId)) : new AdoptedAnimal(new AnimalId(animalId));
    }

    public void setAnimalId(final UUID animalId) {
        this.animalId = animalId;
    }

    public void setInShelter(final Boolean inShelter) {
        this.inShelter = inShelter;
    }
}