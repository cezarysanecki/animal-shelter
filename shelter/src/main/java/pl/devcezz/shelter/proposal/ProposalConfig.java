package pl.devcezz.shelter.proposal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProposalConfig {

    @Bean
    AnimalProposalService animalProposalService(AnimalProposalRepository animalProposalRepository) {
        return new AnimalProposalService(animalProposalRepository);
    }

    @Bean
    ProposalEventHandler proposalEventHandler(AnimalProposalRepository animalProposalRepository) {
        return new ProposalEventHandler(animalProposalRepository);
    }
}
