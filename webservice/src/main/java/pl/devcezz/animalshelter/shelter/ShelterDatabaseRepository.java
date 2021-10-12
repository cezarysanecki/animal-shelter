package pl.devcezz.animalshelter.shelter;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent;
import pl.devcezz.cqrs.event.EventsBus;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AdoptedAnimal;

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
                animal.getId().value().toString(),
                animal.getName().value(),
                animal.getSpecies().name(),
                animal.getAge().value());
    }

    @Override
    public void update(final Animal animal) {
        jdbcTemplate.update("UPDATE shelter_animal a " +
                        "SET a.name = ?, a.species = ?, a.age = ? " +
                        "WHERE a.animal_id = ?",
                animal.getName().value(),
                animal.getSpecies().name(),
                animal.getAge().value(),
                animal.getId().value().toString());
    }

    @Override
    public void delete(final AnimalId animalId) {
        jdbcTemplate.update("DELETE FROM shelter_animal a " +
                        "WHERE a.animal_id = ?",
                animalId.value().toString());
    }

    @Override
    public Option<ShelterAnimal> findBy(final AnimalId animalId) {
        return Try.ofSupplier(() -> of(
                jdbcTemplate.queryForObject(
                        "SELECT a.animal_id, a.name, a.species, a.age, a.adopted_at IS NULL as in_shelter " +
                                "FROM shelter_animal a " +
                                "WHERE a.animal_id = ?",
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
                        "SELECT a.animal_id, a.name, a.species, a.age FROM shelter_animal a WHERE a.adopted_at IS NULL",
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
    String name;
    String species;
    Integer age;

    AvailableAnimal toAvailableAnimal() {
        return new AvailableAnimal(new AnimalId(animalId), new AnimalInformation(name, species, age));
    }

    public void setAnimalId(final UUID animalId) {
        this.animalId = animalId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSpecies(final String species) {
        this.species = species;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }
}

class ShelterAnimalRow {

    UUID animalId;
    String name;
    String species;
    Integer age;
    Boolean inShelter;

    ShelterAnimal toShelterAnimal() {
        AnimalInformation animalInformation = new AnimalInformation(name, species, age);
        return inShelter ?
                new AvailableAnimal(new AnimalId(animalId), animalInformation) :
                new AdoptedAnimal(new AnimalId(animalId), animalInformation);
    }

    public void setAnimalId(final UUID animalId) {
        this.animalId = animalId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public void setSpecies(final String species) {
        this.species = species;
    }

    public void setInShelter(final Boolean inShelter) {
        this.inShelter = inShelter;
    }
}