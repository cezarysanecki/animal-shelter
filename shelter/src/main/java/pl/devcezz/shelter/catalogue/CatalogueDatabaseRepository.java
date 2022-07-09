package pl.devcezz.shelter.catalogue;

import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.shelter.commons.model.Age;
import pl.devcezz.shelter.commons.model.Gender;
import pl.devcezz.shelter.commons.model.Name;
import pl.devcezz.shelter.commons.model.Species;

import java.util.UUID;

import static java.time.Instant.now;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CatalogueDatabaseRepository implements CatalogueRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Animal save(Animal animal) {
        jdbcTemplate.update("" +
                        "INSERT INTO animal " +
                        "(animal_id, name, age, species, gender, status, creation_timestamp, modification_timestamp) VALUES " +
                        "(?, ?, ?, ?, ?, ?, ?, ?)",
                animal.getAnimalId().getValue().toString(),
                animal.getName().getValue(),
                animal.getAge().getValue(),
                animal.getSpecies().getValue(),
                animal.getGender().name(),
                animal.getStatus().name(),
                now(),
                now());
        return animal;
    }

    @Override
    public Animal update(Animal animal) {
        jdbcTemplate.update("" +
                        "UPDATE animal a SET " +
                        "a.name = ?, a.age = ?, a.species = ?, a.gender = ?, a.status = ?, a.modification_timestamp = ? " +
                        "WHERE a.animal_id = ?",
                animal.getName().getValue(),
                animal.getAge().getValue(),
                animal.getSpecies().getValue(),
                animal.getGender().name(),
                animal.getStatus().name(),
                now(),
                animal.getAnimalId().getValue().toString());
        return animal;
    }

    @Override
    public Animal delete(Animal animal) {
        jdbcTemplate.update("" +
                        "DELETE FROM animal a " +
                        "WHERE a.animal_id = ?",
                animal.getAnimalId().getValue().toString());
        return animal;
    }

    @Override
    public Option<Animal> findBy(AnimalId animalId) {
        try {
            return Option.of(
                    jdbcTemplate.queryForObject(
                            "SELECT a.* FROM animal a WHERE a.animal_id = ?",
                            new BeanPropertyRowMapper<>(AnimalDatabaseRow.class),
                            animalId.getValue().toString())
            ).map(AnimalDatabaseRow::toAnimal);
        } catch (EmptyResultDataAccessException e) {
            return Option.none();
        }
    }
}

@Data
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class AnimalDatabaseRow {
    UUID animalId;
    String name;
    Integer age;
    String species;
    Gender gender;
    Status status;

    Animal toAnimal() {
        return Animal.restore(AnimalId.of(animalId), Name.of(name), Age.of(age), Species.of(species), gender, status);
    }
}