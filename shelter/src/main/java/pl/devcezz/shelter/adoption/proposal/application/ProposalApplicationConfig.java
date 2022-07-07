package pl.devcezz.shelter.adoption.proposal.application;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;
import pl.devcezz.shelter.commons.events.DomainEvents;

@Configuration
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ProposalApplicationConfig {

    private final Proposals proposalRepository;
    private final DomainEvents publisher;

    @Bean
    CatalogueOperationsEventsHandler catalogueOperationsEventsHandler() {
        return new CatalogueOperationsEventsHandler(proposalRepository, publisher);
    }

    @Bean
    ShelterOperationsEventsHandler shelterOperationsEventHandler() {
        return new ShelterOperationsEventsHandler(proposalRepository, publisher);
    }
}
