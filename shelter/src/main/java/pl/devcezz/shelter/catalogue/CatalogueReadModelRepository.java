package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatalogueReadModelRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<AnimalDto> findAllFor(List<UUID> animalIds) {
        return jdbcTemplate.queryForList("" +
                        "SELECT a.animal_id, a.name, a.age, a.species, a.gender " +
                        "FROM animal a" +
                        "WHERE a.animal_id IN ?",
                AnimalDto.class,
                animalIds.stream().map(UUID::toString).toList());
    }
}

