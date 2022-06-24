package pl.devcezz.shelter.adoption.proposal.infrastructure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.devcezz.shelter.adoption.proposal.application.AnimalOperationsEventsHandler;
import pl.devcezz.shelter.adoption.proposal.application.ShelterOperationsEventsHandler;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;

@Configuration
public class ProposalConfig {

    @Bean
    AnimalOperationsEventsHandler animalOperationsEventHandler(
            Proposals proposalRepository,
            ApplicationEventPublisher publisher) {
        return new AnimalOperationsEventsHandler(proposalRepository, publisher);
    }

    @Bean
    ShelterOperationsEventsHandler shelterOperationsEventHandler(
            Proposals proposalRepository,
            ApplicationEventPublisher publisher) {
        return new ShelterOperationsEventsHandler(proposalRepository, publisher);
    }

    @Bean
    ProposalDatabaseRepository proposalDatabaseRepository(@Qualifier("adoptionJdbcTemplate") JdbcTemplate jdbcTemplate) {
        return new ProposalDatabaseRepository(jdbcTemplate);
    }
}
