package pl.devcezz.animalshelter.shelter.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class ShelterConfig {

    @Bean
    ShelterFactory shelterFactory(ShelterRepository shelterRepository) {
        return new ShelterFactory(shelterRepository);
    }

    @Bean
    AcceptingAnimal acceptingAnimal(Animals animals, ShelterFactory shelterFactory) {
        return new AcceptingAnimal(animals, shelterFactory);
    }

    @Bean
    EditingAnimal editingAnimal(Animals animals) {
        return new EditingAnimal(animals);
    }

    @Bean
    DeletingAnimal deletingAnimal(Animals animals) {
        return new DeletingAnimal(animals);
    }

    @Bean
    AdoptingAnimal adoptingAnimal(Animals animals) {
        return new AdoptingAnimal(animals);
    }
}
