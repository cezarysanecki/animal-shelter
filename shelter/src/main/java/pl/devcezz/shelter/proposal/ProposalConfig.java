package pl.devcezz.shelter.proposal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProposalConfig {

    @Bean
    AnimalProposalFacade animalProposalFacade(AnimalProposalRepository animalProposalRepository) {
        return new AnimalProposalFacade(animalProposalRepository);
    }

    @Bean
    ProposalEventHandler proposalEventHandler(AnimalProposalRepository animalProposalRepository) {
        return new ProposalEventHandler(animalProposalRepository);
    }
}
