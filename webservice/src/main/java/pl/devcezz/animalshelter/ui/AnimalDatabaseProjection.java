package pl.devcezz.animalshelter.ui;

import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.animalshelter.ui.query.GetAnimalInfoQuery;
import pl.devcezz.animalshelter.ui.query.GetAnimalsQuery;
import pl.devcezz.animalshelter.ui.dto.AnimalDto;
import pl.devcezz.animalshelter.ui.dto.AnimalInfoDto;

import java.time.Instant;
import java.util.UUID;

import static io.vavr.control.Option.none;
import static io.vavr.control.Option.of;

class AnimalDatabaseProjection implements AnimalProjection {

    private final JdbcTemplate jdbcTemplate;

    AnimalDatabaseProjection(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AnimalDto> handle(final GetAnimalsQuery query) {
        return Stream.ofAll(
                jdbcTemplate.query(
                        "SELECT a.animal_id, a.name, a.species, a.age, a.adopted_at IS NULL as in_shelter FROM shelter_animal a",
                        new BeanPropertyRowMapper<>(AnimalRow.class)
                ))
                .map(AnimalRow::toAnimalDto)
                .toList();
    }

    @Override
    public Option<AnimalInfoDto> handle(final GetAnimalInfoQuery query) {
        return Try.ofSupplier(() -> of(
                        jdbcTemplate.queryForObject(
                                "SELECT a.animal_id, a.name, a.species, a.age, a.admitted_at, a.adopted_at FROM shelter_animal a WHERE a.animal_id = ?",
                                new BeanPropertyRowMapper<>(AnimalInfoRow.class),
                                query.animalId().toString()))
                        .map(AnimalInfoRow::toAnimalInfoDto))
                .getOrElse(none());
    }
}

class AnimalRow {

    UUID animalId;
    String name;
    String species;
    Integer age;
    Boolean inShelter;

    AnimalDto toAnimalDto() {
        return new AnimalDto(animalId, name, species, age, inShelter);
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

    public void setInShelter(final Boolean inShelter) {
        this.inShelter = inShelter;
    }
}


class AnimalInfoRow {

    UUID animalId;
    String name;
    String species;
    Integer age;
    Instant admittedAt;
    Instant adoptedAt;

    AnimalInfoDto toAnimalInfoDto() {
        return new AnimalInfoDto(animalId, name, species, age, admittedAt, adoptedAt);
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

    public void setAdmittedAt(final Instant admittedAt) {
        this.admittedAt = admittedAt;
    }

    public void setAdoptedAt(final Instant adoptedAt) {
        this.adoptedAt = adoptedAt;
    }
}
