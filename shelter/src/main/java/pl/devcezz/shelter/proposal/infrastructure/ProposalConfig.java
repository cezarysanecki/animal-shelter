package pl.devcezz.shelter.proposal.infrastructure;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.shelter.proposal.model.ProposalRepository;
import pl.devcezz.shelter.proposal.application.ProposalEventHandler;
import pl.devcezz.shelter.proposal.application.ProposalFacade;

@Configuration
public class ProposalConfig {

    @Bean
    ProposalFacade proposalFacade(
            ProposalRepository proposalRepository,
            ApplicationEventPublisher eventPublisher) {
        return new ProposalFacade(proposalRepository, eventPublisher);
    }

    @Bean
    ProposalEventHandler proposalEventHandler(ProposalRepository proposalRepository) {
        return new ProposalEventHandler(proposalRepository);
    }
}
