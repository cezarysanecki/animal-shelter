package pl.devcezz.animalshelter.animal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class AnimalConfig {

    @Bean
    ShelterFactory shelterFactory(ShelterRepository shelterRepository) {
        return new ShelterFactory(shelterRepository);
    }

    @Bean
    AcceptingAnimal acceptingAnimal(Animals animals, ShelterFactory shelterFactory) {
        return new AcceptingAnimal(animals, shelterFactory);
    }
}
