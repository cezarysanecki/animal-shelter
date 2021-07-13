package pl.csanecki.animalshelter.___;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.csanecki.animalshelter.___.species.Species;

import java.util.List;
import java.util.UUID;

import static io.vavr.collection.List.ofAll;
import static java.util.stream.Collectors.toList;

class ShelterDatabaseRepository implements ShelterRepository {

    private final JdbcTemplate jdbcTemplate;

    ShelterDatabaseRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean contains(final Species species) {
        try {
            jdbcTemplate.queryForObject(
                    "SELECT s.* FROM shelter_species s where s.value = ?",
                    new BeanPropertyRowMapper<>(SpeciesRow.class),
                    species.getValue());
        } catch (EmptyResultDataAccessException e) {
            return false;
        }

        return true;
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
    public AnimalsInShelter queryForAnimalsInShelter() {
        return new AnimalsInShelter(ofAll(
                findAnimalsInShelter()
                        .stream()
                        .map(AnimalRow::toAnimal)
                        .collect(toList())
        ));
    }

    private List<AnimalRow> findAnimalsInShelter() {
        return jdbcTemplate.query(
                "SELECT a.name, a.species, a.age FROM shelter_animal a WHERE a.adoptedAt = NULL",
                new BeanPropertyRowMapper<>(AnimalRow.class));
    }

    @Override
    public Shelter queryForShelter() {
        return findShelter()
                .toShelter();
    }

    private ShelterRow findShelter() {
        return jdbcTemplate.queryForObject(
                "SELECT c.capacity, c.safeThreshold FROM shelter_config c",
                new BeanPropertyRowMapper<>(ShelterRow.class));
    }
}

class ShelterRow {

    private final int capacity;
    private final int safeThreshold;

    ShelterRow(final int capacity, final int safeThreshold) {
        this.capacity = capacity;
        this.safeThreshold = safeThreshold;
    }

    Shelter toShelter() {
        return new Shelter(capacity, safeThreshold);
    }
}

class SpeciesRow {

    private final String value;

    SpeciesRow(final String value) {
        this.value = value;
    }
}

class AnimalRow {

    private final String name;
    private final String species;
    private final int age;

    AnimalRow(final String name, final String species, final int age) {
        this.name = name;
        this.species = species;
        this.age = age;
    }

    Animal toAnimal() {
        return new Animal(UUID.randomUUID(), name, species, age);
    }
}