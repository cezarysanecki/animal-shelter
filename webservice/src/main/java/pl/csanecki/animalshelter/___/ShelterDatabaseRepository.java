package pl.csanecki.animalshelter.___;

import io.vavr.collection.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

class ShelterDatabaseRepository implements ShelterRepository {

    private final JdbcTemplate jdbcTemplate;

    ShelterDatabaseRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Species> findAllSpecies() {
        try {
            return List.ofAll(
                    jdbcTemplate.query(
                            "SELECT s.* FROM shelter_species s",
                            new BeanPropertyRowMapper<>(SpeciesRow.class))
            ).map(SpeciesRow::toSpecies);
        } catch (EmptyResultDataAccessException e) {
            return List.empty();
        }
    }

    @Override
    public void save(final Species species) {
        jdbcTemplate.update("" +
                        "INSERT INTO shelter_species " +
                        "(value) VALUES " +
                        "(?)",
                species.getValue());
    }
}

class SpeciesRow {

    private final String value;

    SpeciesRow(final String value) {
        this.value = value;
    }

    Species toSpecies() {
        return Species.of(value);
    }
}