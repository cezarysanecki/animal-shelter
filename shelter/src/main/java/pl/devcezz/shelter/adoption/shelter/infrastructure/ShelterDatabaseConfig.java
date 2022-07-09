package pl.devcezz.shelter.adoption.shelter.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.shelter.adoption.shelter.model.ShelterFactory;
import pl.devcezz.shelter.commons.events.DomainEvents;

@Configuration
public class ShelterDatabaseConfig {

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
