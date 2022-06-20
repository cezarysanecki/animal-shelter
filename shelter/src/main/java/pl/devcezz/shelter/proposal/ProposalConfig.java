package pl.devcezz.shelter.proposal;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProposalConfig {

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
