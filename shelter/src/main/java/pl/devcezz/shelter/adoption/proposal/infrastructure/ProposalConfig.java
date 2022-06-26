package pl.devcezz.shelter.adoption.proposal.infrastructure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.shelter.adoption.proposal.application.AnimalOperationsEventsHandler;
import pl.devcezz.shelter.adoption.proposal.application.ShelterOperationsEventsHandler;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;
import pl.devcezz.shelter.commons.events.DomainEvents;

@Configuration
public class ProposalConfig {

    @Bean
    ProposalDatabaseRepository proposalDatabaseRepository(@Qualifier("adoption") JdbcTemplate jdbcTemplate) {
        return new ProposalDatabaseRepository(jdbcTemplate);
    }

    @Bean
    AnimalOperationsEventsHandler animalOperationsEventHandler(
            Proposals proposalRepository,
            DomainEvents publisher) {
        return new AnimalOperationsEventsHandler(proposalRepository, publisher);
    }

    @Bean
    ShelterOperationsEventsHandler shelterOperationsEventHandler(
            Proposals proposalRepository,
            DomainEvents publisher) {
        return new ShelterOperationsEventsHandler(proposalRepository, publisher);
    }
}
