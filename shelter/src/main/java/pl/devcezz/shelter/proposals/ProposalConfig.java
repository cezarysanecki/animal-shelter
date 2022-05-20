package pl.devcezz.shelter.proposals;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProposalConfig {

    @Bean
    ProposalEventHandler proposalEventHandler(AnimalProposalRepository animalProposalRepository) {
        return new ProposalEventHandler(animalProposalRepository);
    }
}
