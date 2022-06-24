package pl.devcezz.shelter.adoption.shelter.infrastructure;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.shelter.adoption.shelter.application.AcceptingProposal;
import pl.devcezz.shelter.adoption.shelter.application.FindPendingProposal;
import pl.devcezz.shelter.adoption.shelter.model.ShelterFactory;
import pl.devcezz.shelter.adoption.shelter.model.Shelters;

@Configuration
public class ShelterConfig {

    @Bean
    AcceptingProposal acceptingProposal(
            FindPendingProposal findPendingProposal,
            Shelters shelterRepository) {
        return new AcceptingProposal(findPendingProposal, shelterRepository);
    }

    @Bean
    ShelterDatabaseRepository shelterDatabaseRepository(
            AcceptedProposalsDatabaseRepository acceptedProposalsDatabaseRepository,
            ApplicationEventPublisher publisher) {
        ShelterFactory shelterFactory = new ShelterFactory();
        return new ShelterDatabaseRepository(
                acceptedProposalsDatabaseRepository,
                new DomainModelMapper(shelterFactory),
                publisher);
    }
}
