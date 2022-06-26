package pl.devcezz.shelter.catalogue;

import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static java.time.Instant.now;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
class AnimalRepository {

    private final JdbcTemplate jdbcTemplate;

    Animal saveNew(Animal animal) {
        jdbcTemplate.update("" +
                        "INSERT INTO animal " +
                        "(animal_id, name, age, species, gender, creation_timestamp, modification_timestamp) VALUES " +
                        "(?, ?, ?, ?, ?, ?, ?)",
                animal.getAnimalId().getValue().toString(),
                animal.getName(),
                animal.getAge(),
                animal.getSpecies(),
                animal.getGender().name(),
                now(),
                now());
        return animal;
    }

    Animal update(Animal animal) {
        jdbcTemplate.update("" +
                        "UPDATE animal a SET " +
                        "a.name = ?, a.age = ?, a.species = ?, a.gender = ?, a.modification_timestamp = ? " +
                        "WHERE a.animal_id = ?",
                animal.getName(),
                animal.getAge(),
                animal.getSpecies(),
                animal.getGender().name(),
                now(),
                animal.getAnimalId().getValue().toString());
        return animal;
    }

    Animal updateStatus(Animal animal) {
        jdbcTemplate.update("" +
                        "UPDATE animal a SET " +
                        "a.status = ?, a.modification_timestamp = ? " +
                        "WHERE a.animal_id = ?",
                animal.getStatus().name(),
                now(),
                animal.getAnimalId().getValue().toString());
        return animal;
    }

    Option<Animal> findByAnimalId(AnimalId animalId) {
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
        return Animal.of(animalId, name, age, species, gender, status);
    }
}