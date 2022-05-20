package pl.devcezz.shelter.catalogue;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ShelterCatalogueConfig {

    @Bean
    AnimalService animalService(
            AnimalRepository animalRepository,
            ApplicationEventPublisher eventPublisher) {
        return new AnimalService(animalRepository, eventPublisher);
    }
}
