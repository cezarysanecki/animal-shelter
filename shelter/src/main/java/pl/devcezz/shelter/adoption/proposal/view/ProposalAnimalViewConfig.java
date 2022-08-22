package pl.devcezz.shelter.adoption.proposal.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProposalAnimalViewConfig {

    @Bean
    CatalogueViewEventsHandler catalogueViewEventsHandler(
            ProposalAnimalDatabase proposalAnimalDatabase) {
        return new CatalogueViewEventsHandler(proposalAnimalDatabase);
    }
}
