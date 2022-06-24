package pl.devcezz.shelter.adoption.proposal.infrastructure;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.shelter.adoption.proposal.application.AnimalOperationsEventHandler;
import pl.devcezz.shelter.adoption.proposal.application.ShelterOperationsEventHandler;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;

@Configuration
public class ProposalConfig {

    @Bean
    AnimalOperationsEventHandler animalOperationsEventHandler(
            Proposals proposalRepository,
            ApplicationEventPublisher publisher) {
        return new AnimalOperationsEventHandler(proposalRepository, publisher);
    }

    @Bean
    ShelterOperationsEventHandler shelterOperationsEventHandler(
            Proposals proposalRepository,
            ApplicationEventPublisher publisher) {
        return new ShelterOperationsEventHandler(proposalRepository, publisher);
    }
}
