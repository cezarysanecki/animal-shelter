package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatalogueReadModelRepository {

    private final NamedParameterJdbcOperations jdbcTemplate;

    public List<AnimalDto> findAllFor(List<UUID> animalIds) {
        return jdbcTemplate.query("" +
                        "SELECT a.animal_id, a.name, a.age, a.species, a.gender " +
                        "FROM animal a " +
                        "WHERE a.animal_id IN (:animalIds)",
                Map.of("animalIds", animalIds.stream().map(Object::toString).toList()),
                new AnimalRowMapper());
    }
}

class AnimalRowMapper implements RowMapper<AnimalDto> {

    @Override
    public AnimalDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AnimalDto.builder()
                .animalId(UUID.fromString(rs.getString("animal_id")))
                .name(rs.getString("name"))
                .age(rs.getInt("age"))
                .species(rs.getString("species"))
                .gender(rs.getString("gender"))
                .build();
    }
}

