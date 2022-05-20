package pl.devcezz.shelter.catalogue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ShelterCatalogueConfig {

    @Bean
    AnimalService animalService(AnimalRepository animalRepository) {
        return new AnimalService(animalRepository);
    }
}
