package pl.csanecki.animalshelter.webservice.write;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pl.csanecki.animalshelter.webservice.write.add.AddAnimalCommand;
import pl.csanecki.animalshelter.webservice.repository.DatabaseRuntimeError;

import java.util.UUID;

public class ShelterJdbcWriteRepository implements ShelterWriteRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShelterJdbcWriteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void registerAnimal(AddAnimalCommand command) {
        int rowAffected = jdbcTemplate.update(
                "INSERT INTO animals(name, kind, age, uuid) VALUES(?, ?, ?, ?)",
                command.name, command.kind, command.age, command.animalId.toString());

        if (rowAffected == 0) {
            throw new DatabaseRuntimeError("Cannot insert animal of id: " + command.animalId);
        }
    }

    @Override
    public void updateAdoptedAtToNow(UUID animalId) {
        int rowAffected = jdbcTemplate.update(
                "UPDATE animals SET adoptedAt = NOW() WHERE uuid = ? AND adoptedAt IS NULL",
                animalId.toString());

        if (rowAffected == 0) {
            throw new DatabaseRuntimeError("Cannot adopt animal of id: " + animalId);
        }
    }
}