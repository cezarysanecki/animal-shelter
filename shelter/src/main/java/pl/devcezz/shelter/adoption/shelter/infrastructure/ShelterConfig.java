package pl.devcezz.shelter.adoption.shelter.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.shelter.adoption.shelter.application.AcceptingProposal;
import pl.devcezz.shelter.adoption.shelter.application.CancelingProposal;
import pl.devcezz.shelter.adoption.shelter.application.FindAcceptedProposal;
import pl.devcezz.shelter.adoption.shelter.application.FindPendingProposal;
import pl.devcezz.shelter.adoption.shelter.model.ShelterFactory;
import pl.devcezz.shelter.adoption.shelter.model.Shelters;
import pl.devcezz.shelter.commons.events.DomainEvents;

@Configuration
public class ShelterConfig {

    @Bean
    AcceptingProposal acceptingProposal(
            FindPendingProposal findPendingProposal,
            Shelters shelterRepository) {
        return new AcceptingProposal(findPendingProposal, shelterRepository);
    }

    @Bean
    CancelingProposal cancelingProposal(
            FindAcceptedProposal findAcceptedProposal,
            Shelters shelterRepository) {
        return new CancelingProposal(findAcceptedProposal, shelterRepository);
    }

    @Bean
    ShelterDatabaseRepository shelterDatabaseRepository(
            AcceptedProposalsDatabaseRepository acceptedProposalsDatabaseRepository,
            DomainEvents publisher) {
        ShelterFactory shelterFactory = new ShelterFactory();
        return new ShelterDatabaseRepository(
                acceptedProposalsDatabaseRepository,
                new DomainModelMapper(shelterFactory),
                publisher);
    }
}
