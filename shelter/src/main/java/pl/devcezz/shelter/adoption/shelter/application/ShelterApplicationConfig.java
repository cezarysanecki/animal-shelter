package pl.devcezz.shelter.adoption.shelter.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.devcezz.shelter.adoption.shelter.model.Shelters;

@Configuration
public class ShelterApplicationConfig {

    private final Shelters shelterRepository;
    private final CancelingProposal cancelingProposal;

    ShelterApplicationConfig(Shelters shelterRepository) {
        this.shelterRepository = shelterRepository;
        this.cancelingProposal = new CancelingProposal(shelterRepository);
    }

    @Bean
    AcceptingProposal acceptingProposal() {
        return new AcceptingProposal(shelterRepository);
    }

    @Bean
    CancelingProposal cancelingProposal() {
        return cancelingProposal;
    }

    @Bean
    ShelterEventsHandler shelterEventsHandler() {
        return new ShelterEventsHandler(cancelingProposal);
    }
}
