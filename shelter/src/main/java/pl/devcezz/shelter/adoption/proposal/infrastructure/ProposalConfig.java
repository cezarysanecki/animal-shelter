package pl.devcezz.shelter.adoption.proposal.infrastructure;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.devcezz.shelter.adoption.proposal.application.AnimalOperationsEventsHandler;
import pl.devcezz.shelter.adoption.proposal.application.ShelterOperationsEventsHandler;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;
import pl.devcezz.shelter.commons.events.DomainEvents;

@Configuration
@Import(ProposalDatabaseConfig.class)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ProposalConfig {

    private final Proposals proposalRepository;
    private final DomainEvents publisher;

    @Bean
    AnimalOperationsEventsHandler animalOperationsEventHandler() {
        return new AnimalOperationsEventsHandler(proposalRepository, publisher);
    }

    @Bean
    ShelterOperationsEventsHandler shelterOperationsEventHandler() {
        return new ShelterOperationsEventsHandler(proposalRepository, publisher);
    }
}
