package pl.devcezz.shelter.adoption.proposal.view;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ProposalAnimalDatabase {

    private final JdbcTemplate jdbcTemplate;

    public void save(ProposalAnimalData animalData) {
        insert(animalData);
    }

    private int insert(ProposalAnimalData animalData) {
        return jdbcTemplate.update("" +
                        "INSERT INTO proposal_animal " +
                        "(proposal_id, name, age, species, gender) VALUES " +
                        "(?, ?, ?, ?, ?)",
                animalData.getProposalId().getValue(),
                animalData.getName().getValue(),
                animalData.getAge().getValue(),
                animalData.getSpecies().getValue(),
                animalData.getGender().toString());
    }
}
