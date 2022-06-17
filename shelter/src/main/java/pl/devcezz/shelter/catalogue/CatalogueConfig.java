package pl.devcezz.shelter.catalogue;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CatalogueConfig {

    @Bean
    AnimalFacade animalFacade(
            AnimalRepository animalRepository,
            ApplicationEventPublisher eventPublisher) {
        return new AnimalFacade(animalRepository, eventPublisher);
    }
}