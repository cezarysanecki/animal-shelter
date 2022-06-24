package pl.devcezz.shelter.catalogue;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CatalogueDatabaseConfig.class})
public class CatalogueConfig {

    @Bean
    AnimalFacade animalFacade(
            AnimalRepository animalRepository,
            ApplicationEventPublisher eventPublisher) {
        return new AnimalFacade(animalRepository, eventPublisher);
    }

    @Bean
    AnimalEventHandler animalEventHandler(
            AnimalRepository animalRepository) {
        return new AnimalEventHandler(animalRepository);
    }
}